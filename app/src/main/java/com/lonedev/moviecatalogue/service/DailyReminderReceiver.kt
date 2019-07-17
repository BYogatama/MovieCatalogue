/*
 * Created by Bagus Yogatama on 7/14/19 12:58 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/14/19 12:58 PM
 */

package com.lonedev.moviecatalogue.service

import android.content.Context
import android.content.Intent
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseBroadcastReceiver
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.FavoriteActivity

class DailyReminderReceiver : BaseBroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID: Int = 101
        const val NOTIFICATION_CHANNEL_ID: String = "100"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appName = context?.resources?.getString(R.string.app_name)
        val message = context?.resources?.getString(R.string.comeback_again)

        displayReminder(
            context, appName, message,
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID,
            "DISPLAY_REMINDER"
        )
    }

    override fun notificationIntent(context: Context?, intent: Intent?): Intent {
        return Intent(context, FavoriteActivity::class.java)
    }

}