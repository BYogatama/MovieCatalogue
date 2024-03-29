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
import com.lonedev.moviecatalogue.service.DailyReminderReceiver
import java.util.*

object Preferences {
    fun setupDailyReminder(context: Context?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val hourOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        createAlarm(
            alarmManager, hourOfDay, getPendingIntent(
                context,
                DailyReminderReceiver.NOTIFICATION_ID,
                DailyReminderReceiver::class.java
            )
        )
    }

    fun disableDailyReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        cancelAlarm(
            alarmManager, getPendingIntent(
                context,
                DailyReminderReceiver.NOTIFICATION_ID,
                DailyReminderReceiver::class.java
            )
        )
    }

    private fun <T> getPendingIntent(context: Context?, notificationId: Int, receiver: Class<T>): PendingIntent? {
        val intent = Intent(context, receiver)
        return if (PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_NO_CREATE) == null) {
            createPendingIntent(context, notificationId, intent) // Only Create when PendingIntent Null
        } else {
            null
        }
    }

    private fun createPendingIntent(context: Context?, notificationId: Int, pendingIntent: Intent): PendingIntent? {
        return PendingIntent.getBroadcast(context, notificationId, pendingIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createAlarm(alarmManager: AlarmManager, hourOfDay: Calendar, pendingIntent: PendingIntent?) {
        if (pendingIntent != null) {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, hourOfDay.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
            )
        }
    }

    private fun cancelAlarm(alarmManager: AlarmManager, pendingIntent: PendingIntent?) {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}