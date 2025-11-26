package com.github.android.unofficial.webview

import android.util.Log
import android.webkit.JavascriptInterface


class WebAppBridge {

    @JavascriptInterface
    fun sendMessage(message: String) {
        Log.d("WebAppBridge", "Received message from JS: $message")
    }
}
