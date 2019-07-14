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
import android.os.Build
import com.lonedev.moviecatalogue.service.DailyReminder
import java.util.*

object Preferences {
    fun setupDailyReminder(context: Context?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(getPendingIntent(context))

        val hourOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                hourOfDay.timeInMillis,
                getPendingIntent(context)
            )
        } else {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, hourOfDay.timeInMillis, AlarmManager.INTERVAL_DAY,
                getPendingIntent(context)
            )
        }
    }

    fun disableDailyReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context))
    }

    private fun getPendingIntent(context: Context): PendingIntent? {
        val dailyReminder = Intent(context, DailyReminder::class.java)

        return PendingIntent.getBroadcast(
            context, DailyReminder.NOTIFICATION_ID, dailyReminder,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }
}