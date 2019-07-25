/*
 * Created by Bagus Yogatama on 7/15/19 11:06 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/15/19 11:06 PM
 */

package com.lonedev.moviecatalogue.base

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.lonedev.moviecatalogue.R

abstract class BaseBroadcastReceiver<T> : BroadcastReceiver() {

    private lateinit var nextIntent : Intent

    override fun onReceive(context: Context?, intent: Intent?) {
        nextIntent = notificationIntent(context, intent)
    }

    fun displayReminder(
        context: Context?, title: String?, message: String?,
        result : T?, notificationId: Int, notificationChannelId: String, notificationChannelName: String
    ) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        nextIntent.putExtra("bundle", addExtraToIntent(result))

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, notificationChannelId)
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
                notificationChannelId,
                notificationChannelName, notificationChannelImportance
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

    abstract fun notificationIntent(context: Context?, intent: Intent?): Intent

    abstract fun addExtraToIntent(result: T?) : Bundle
}