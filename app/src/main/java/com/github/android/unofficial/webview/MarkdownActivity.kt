package com.github.android.unofficial.webview

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.android.unofficial.R

class MarkdownActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_markdown)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // Attach JS bridge
        webView.addJavascriptInterface(WebAppBridge(), "native")

        // Ensure we know when the page is ready
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("Renderer", "Template loaded: $url")
                injectSampleMarkdown()
            }
        }

        // Load the template asset
        webView.loadUrl("file:///android_asset/template.html")
    }

    private fun injectSampleMarkdown() {
        val html = """
            <h1>Hello GitHub</h1>
            <p>This is rendered Markdown content inside the WebView.</p>
        """.trimIndent()

        val js = """
            javascript:injectContent(
                "render-1",
                ${html.quoteForJs()},
                true,
                true,
                "-1",
                "false"
            )
        """.trimIndent()

        webView.evaluateJavascript(js, null)
    }

    // Helper to escape quotes for JS injection
    private fun String.quoteForJs(): String {
        return "\"" + replace("\"", "\\\"").replace("\n", "\\n") + "\""
    }
}
