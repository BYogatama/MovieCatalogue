/*
 * Created by Bagus Yogatama on 7/15/19 11:45 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/15/19 11:45 PM
 */

package com.lonedev.moviecatalogue.service

import android.content.Context
import android.content.Intent
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseBroadcastReceiver
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity

class TVSeriesReleaseReminder : BaseBroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID: Int = 202
        const val NOTIFICATION_CHANNEL_ID: String = "200"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appName = context?.resources?.getString(R.string.app_name)

        val bundleExtras = intent?.getBundleExtra("tv")
        val tvSeriesResult = bundleExtras?.getParcelable<TVSeriesResult>("tv")

        displayReminder(
            context,
            appName,
            context?.getString(R.string.release_reminder_message)?.let { String.format(it, tvSeriesResult?.name) },
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID,
            "RELEASE_REMINDER"
        )
    }

    override fun notificationIntent(context: Context?, intent: Intent?): Intent {
        val bundleExtras = intent?.getBundleExtra("tv")
        val tvSeriesResult = bundleExtras?.getParcelable<TVSeriesResult>("tv")
        return TVSeriesDetailActivity.generateIntent(context, tvSeriesResult)
    }
}