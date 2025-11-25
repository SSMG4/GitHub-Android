package com.github.android.unofficial.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SyncService : Service() {
  override fun onBind(intent: Intent?): IBinder? = null

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    // Placeholder: schedule WorkManager jobs here
    stopSelf()
    return START_NOT_STICKY
  }
}
