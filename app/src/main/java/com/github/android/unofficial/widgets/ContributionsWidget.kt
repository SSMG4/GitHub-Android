package com.github.android.unofficial.widgets

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class ContributionsWidget : RemoteViewsService() {
  override fun onGetViewFactory(intent: android.content.Intent?): RemoteViewsFactory {
    return Factory(this)
  }

  class Factory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    override fun onCreate() {}
    override fun onDataSetChanged() {}
    override fun onDestroy() {}
    override fun getCount(): Int = 1
    override fun getViewAt(position: Int): RemoteViews {
      // Minimal placeholder
      return RemoteViews(context.packageName, android.R.layout.simple_list_item_1).apply {
        setTextViewText(android.R.id.text1, "Contributions widget")
      }
    }
    override fun getLoadingView(): RemoteViews? = null
    override fun getViewTypeCount(): Int = 1
    override fun getItemId(position: Int): Long = position.toLong()
    override fun hasStableIds(): Boolean = true
  }
}
