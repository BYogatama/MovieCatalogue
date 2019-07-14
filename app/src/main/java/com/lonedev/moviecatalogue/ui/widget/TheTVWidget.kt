/*
 * Created by Bagus Yogatama on 7/9/19 10:19 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/9/19 10:19 PM
 */

package com.lonedev.moviecatalogue.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.ui.widget.services.TheTVWidgetService

class TheTVWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val mgr = AppWidgetManager.getInstance(context)
            val cn = ComponentName(context!!, TheTVWidget::class.java)
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.stack_view)
        }
        super.onReceive(context, intent)
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, TheTVWidgetService::class.java)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val views = RemoteViews(context.packageName, R.layout.the_tvwidget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun refreshWidget(context: Context) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.component = ComponentName(context, TheTVWidget::class.java)
            context.sendBroadcast(intent)
        }
    }
}

