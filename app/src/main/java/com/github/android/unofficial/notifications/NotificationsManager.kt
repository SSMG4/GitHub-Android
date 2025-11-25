package com.github.android.unofficial.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationsManager {
  const val CHANNEL_ID = "gh_unofficial_default"

  fun ensureChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      val channel = NotificationChannel(
        CHANNEL_ID,
        "GitHub Unofficial",
        NotificationManager.IMPORTANCE_DEFAULT
      )
      nm.createNotificationChannel(channel)
    }
  }
}
