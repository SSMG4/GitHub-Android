package com.github.android.unofficial.webview

import android.webkit.WebView
import org.json.JSONObject

class RendererController(
  private val webView: WebView,
  private val bus: MessageBus
) {

  fun bind(handler: MessageHandler) {
    bus.attach(handler)
  }

  fun unbind() {
    bus.detach()
  }

  fun load(
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
          "${escape(id)}",
          ${JSONObject.quote(html)},
          ${if (commitEnabled) "true" else "false"},
          ${if (tasksEnabled) "true" else "false"},
          "${escape(overridePos)}",
          "${escape(overrideVal)}"
        );
      })();
    """.trimIndent()
    webView.evaluateJavascript(js, null)
  }

  fun anchorPosition(anchor: String) {
    val js = """
      (function(){
        if (!window.github) return;
        window.github.getAnchorPosition("${escape(anchor)}");
      })();
    """.trimIndent()
    webView.evaluateJavascript(js, null)
  }

  private fun escape(s: String): String = s
    .replace("\\", "\\\\")
    .replace("\"", "\\\"")
    .replace("\n", "\\n")
}
