/*
 * Created by Bagus Yogatama on 7/14/19 2:10 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/14/19 2:10 PM
 */

package com.lonedev.moviecatalogue.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.service.DailyReminderReceiver
import com.lonedev.moviecatalogue.service.MovieReleaseReminderReceiver
import com.lonedev.moviecatalogue.service.TVSeriesReleaseReminder
import java.util.*

object Preferences {
    fun setupDailyReminder(context: Context?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val hourOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        createAlarm(alarmManager, hourOfDay, getDailyReminderPendingIntent(context))
    }

    fun setupMovieReleaseReminder(context: Context?, movie: MovieResult) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val hourOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        createAlarm(alarmManager, hourOfDay, getMovieReleasePendingIntent(context, movie))
    }

    fun setupTVSeriesReleaseReminder(context: Context?, tvSeries: TVSeriesResult) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(getTVSeriesReleasePendingIntent(context, tvSeries))

        val hourOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        createAlarm(alarmManager, hourOfDay, getTVSeriesReleasePendingIntent(context, tvSeries))
    }

    fun disableMovieReleaseReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getMovieReleasePendingIntent(context, MovieResult()))
    }

    fun disableTVSeriesReleaseReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getTVSeriesReleasePendingIntent(context, TVSeriesResult()))
    }

    fun disableDailyReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getDailyReminderPendingIntent(context))
    }

    private fun getDailyReminderPendingIntent(context: Context?): PendingIntent? {
        val dailyReminder = Intent(context, DailyReminderReceiver::class.java)

        return createPendingIntent(context, dailyReminder)
    }

    private fun getMovieReleasePendingIntent(context: Context?, movie: MovieResult?): PendingIntent? {
        val releaseReminder = Intent(context, MovieReleaseReminderReceiver::class.java)

        val bundleExtra = Bundle().apply {
            putParcelable("movie", movie)
        }
        releaseReminder.putExtra("movie", bundleExtra)

        return createPendingIntent(context, releaseReminder)
    }

    private fun getTVSeriesReleasePendingIntent(context: Context?, tvSeries: TVSeriesResult?): PendingIntent? {
        val releaseReminder = Intent(context, TVSeriesReleaseReminder::class.java)
        val bundleExtra = Bundle().apply {
            putParcelable("tv", tvSeries)
        }
        releaseReminder.putExtra("tv", bundleExtra)

        return createPendingIntent(context, releaseReminder)
    }

    private fun createPendingIntent(
        context: Context?, pendingIntent: Intent
    ): PendingIntent? {
        return if (PendingIntent.getBroadcast(
                context, DailyReminderReceiver.NOTIFICATION_ID, pendingIntent,
                PendingIntent.FLAG_NO_CREATE
            ) != null
        ) {
            PendingIntent.getBroadcast(
                context, DailyReminderReceiver.NOTIFICATION_ID, pendingIntent,
                PendingIntent.FLAG_NO_CREATE
            )
        } else {
            PendingIntent.getBroadcast(
                context, DailyReminderReceiver.NOTIFICATION_ID, pendingIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
    }

    private fun createAlarm(
        alarmManager: AlarmManager,
        hourOfDay: Calendar,
        pendingIntent: PendingIntent?
    ) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, hourOfDay.timeInMillis, AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}