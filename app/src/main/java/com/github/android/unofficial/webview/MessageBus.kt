package com.github.android.unofficial.webview

import android.util.Log
import org.json.JSONObject

typealias MessageHandler = (name: String, payload: JSONObject) -> Unit

class MessageBus {
  private var handler: MessageHandler? = null

  fun attach(handler: MessageHandler) {
    this.handler = handler
  }

  fun detach() {
    this.handler = null
  }

  fun dispatch(json: String) {
    try {
      val payload = JSONObject(json)
      val name = payload.optString("messageName", "")
      handler?.invoke(name, payload)
    } catch (t: Throwable) {
      Log.e("RendererBridge", "Failed to parse message: $json", t)
    }
  }
}
