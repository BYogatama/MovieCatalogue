/*
 * Created by Bagus Yogatama on 7/15/19 11:04 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/15/19 11:04 PM
 */

package com.lonedev.moviecatalogue.service

import android.content.Context
import android.content.Intent
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseBroadcastReceiver
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import java.util.*

class MovieReleaseReminderReceiver : BaseBroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID: Int = 201
        const val NOTIFICATION_CHANNEL_ID: String = "200"
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appName = context?.resources?.getString(R.string.app_name)

        val bundleExtras = intent?.getBundleExtra("movie")
        val movieResult: MovieResult? = bundleExtras?.getParcelable("movie")

        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val currentTime = System.currentTimeMillis()

        if (currentTime <= scheduledTime) {
            displayReminder(
                context,
                appName,
                context?.getString(R.string.release_reminder_message)?.let { String.format(it, movieResult?.title) },
                NOTIFICATION_ID,
                NOTIFICATION_CHANNEL_ID,
                "RELEASE_REMINDER"
            )
        }

    }

    override fun notificationIntent(context: Context?, intent: Intent?): Intent {
        val bundleExtras = intent?.getBundleExtra("movie")
        val movieResult: MovieResult? = bundleExtras?.getParcelable("movie")
        return MovieDetailActivity.generateIntent(context, movieResult)
    }


}