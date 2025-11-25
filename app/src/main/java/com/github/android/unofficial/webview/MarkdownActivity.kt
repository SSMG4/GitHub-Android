package com.github.android.unofficial.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.android.unofficial.databinding.ActivityMarkdownBinding
import org.json.JSONObject

class MarkdownActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMarkdownBinding

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMarkdownBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val webView = binding.webView
    val isDark = /* derive from system theme */ false
    val template = if (isDark) "file:///android_asset/webview/template_dark.html"
                   else "file:///android_asset/webview/template.html"

    WebView.setWebContentsDebuggingEnabled(true)

    with(webView.settings) {
      javaScriptEnabled = true
      domStorageEnabled = true
      loadsImagesAutomatically = true
      cacheMode = WebSettings.LOAD_DEFAULT
      builtInZoomControls = false
      displayZoomControls = false
      allowFileAccess = true // needed for file:///android_asset
      allowContentAccess = true
      mediaPlaybackRequiresUserGesture = false
    }

    webView.webChromeClient = WebChromeClient()
    webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        // Let GitHub links open externally or handle specific deep links
        return false
      }
    }

    // JS ↔ Native bridge
    webView.addJavascriptInterface(object {
      @JavascriptInterface
      fun sendMessage(payload: String) {
        handleMessage(JSONObject(payload))
      }
    }, "native")

    // Load template first
    webView.loadUrl(template)

    // After page loads, inject content by calling window.github.load(...)
    webView.evaluateJavascript("""
      (function() {
        if (window.github) { return 'ready'; }
        return 'not_ready';
      })();
    """.trimIndent()) { _ ->
      // Delay a bit or attach to DOMContentLoaded; we can poll readiness:
      injectContent(webView, id = "render-1", html = getHtmlPayload(), commitEnabled = true, tasksEnabled = true, overridePos = "-1", overrideVal = "false")
    }
  }

  private fun injectContent(
    webView: WebView,
    id: String,
    html: String,
    commitEnabled: Boolean,
    tasksEnabled: Boolean,
    overridePos: String,
    overrideVal: String
  ) {
    val js = """
      (function(){
        if (!window.github) return;
        window.github.load(
          "$id",
          ${JSONObject.quote(html)},
          ${if (commitEnabled) "true" else "false"},
          ${if (tasksEnabled) "true" else "false"},
          "$overridePos",
          "$overrideVal"
        );
      })();
    """.trimIndent()
    webView.evaluateJavascript(js, null)
  }

  private fun handleMessage(msg: JSONObject) {
    val name = msg.optString("messageName")
    when (name) {
      "height" -> {
        val height = msg.optInt("height", 0)
        // Update container size if embedding in scroll; or ignore if WebView manages itself
      }
      "scroll_to" -> {
        val posY = msg.optDouble("posY", 0.0)
        // Optionally scroll WebView, or parent, to posY
      }
      "commit_suggestion" -> {
        val id = msg.optString("suggestionId")
        val previewHTML = msg.optString("previewHTML")
        // Bubble this to native UI to confirm and apply
      }
      "task_changed" -> {
        val position = msg.optInt("taskPosition")
        val checked = msg.optBoolean("taskChecked")
        // Update task completion state via API
      }
      "error" -> {
        val errorMsg = msg.optString("message")
        // Log/report
      }
    }
  }

  private fun getHtmlPayload(): String {
    // This HTML must be GitHub-flavored markdown already converted to HTML
    // For now: a simple sample block verifying styles and checkbox handling
    return """
      <article class="markdown-body">
        <h1>Renderer verification</h1>
        <p>This is a test block with <code>inline code</code> and a table.</p>
        <ul class="contains-task-list">
          <li class="task-list-item">
            <input class="task-list-item-checkbox" type="checkbox" disabled> Item A
          </li>
          <li class="task-list-item">
            <input class="task-list-item-checkbox" type="checkbox" disabled> Item B
          </li>
        </ul>
        <pre><code class="language-kotlin">val x = 42</code></pre>
      </article>
    """.trimIndent()
  }
}
