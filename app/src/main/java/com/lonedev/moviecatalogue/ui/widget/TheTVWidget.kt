/*
 * Created by Bagus Yogatama on 7/9/19 10:19 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/9/19 10:19 PM
 */

package com.lonedev.moviecatalogue.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity
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
            updateWidgetData(context)
        } else if (action == TheMovieWidget.ACTION_CLICK) {
            openDetail(intent, context)

        }
        super.onReceive(context, intent)
    }

    private fun openDetail(intent: Intent, context: Context?) {
        val bundle = intent.getBundleExtra(EXTRA_TV)

        val detailIntent = Intent(context, TVSeriesDetailActivity::class.java).apply {
            putExtra("bundle", bundle)
        }

        context?.startActivity(detailIntent)
    }

    private fun updateWidgetData(context: Context?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context!!, TheTVWidget::class.java)
        appWidgetManager.notifyAppWidgetViewDataChanged(
            appWidgetManager.getAppWidgetIds(componentName), R
                .id.stack_view
        )
    }

    companion object {

        const val ACTION_CLICK = "actionClick"
        const val EXTRA_TV = "tv"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, TheTVWidgetService::class.java)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val clickIntent = Intent(context, TheTVWidget::class.java).apply {
                action = ACTION_CLICK
            }
            val clickPendingIntent = PendingIntent.getBroadcast(
                context, 0, clickIntent, 0
            )

            val views = RemoteViews(context.packageName, R.layout.the_tvwidget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)
            views.setPendingIntentTemplate(R.id.stack_view, clickPendingIntent)


            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun refreshWidget(context: Context) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            intent.component = ComponentName(context, TheTVWidget::class.java)
            context.sendBroadcast(intent)
        }
    }
}

