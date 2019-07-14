/*
 * Created by Bagus Yogatama on 7/14/19 12:58 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/14/19 12:58 PM
 */

package com.lonedev.moviecatalogue.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.FavoriteActivity

class DailyReminder : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID: Int = 101
        const val NOTIFICATION_CHANNEL_ID: String = "100"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val appName = context?.resources?.getString(R.string.app_name)
        val message = context?.resources?.getString(R.string.comeback_again)
        displayDailyReminder(context, appName, message, NOTIFICATION_ID)
    }

    private fun displayDailyReminder(context: Context?, title: String?, message: String?, notificationId: Int) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val favouriteActivity = Intent(context, FavoriteActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            favouriteActivity,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_local_movies_white_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(notificationSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannelImportance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "DAILY_REMINDER", notificationChannelImportance
            ).apply {
                enableLights(true)
                enableVibration(true)

                lightColor = Color.BLUE
                vibrationPattern = longArrayOf(100, 200, 100, 200, 100, 200)
            }

            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())

    }

}