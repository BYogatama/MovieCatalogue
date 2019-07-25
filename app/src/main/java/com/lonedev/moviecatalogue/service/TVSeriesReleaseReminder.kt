/*
 * Created by Bagus Yogatama on 7/15/19 11:45 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/15/19 11:45 PM
 */

package com.lonedev.moviecatalogue.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseBroadcastReceiver
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TVSeriesReleaseReminder : BaseBroadcastReceiver<TVSeriesResult>() {

    private var compositeDisposable = CompositeDisposable()

    companion object {
        const val NOTIFICATION_ID: Int = 202
        const val NOTIFICATION_CHANNEL_ID: String = "200"
    }

    @Inject
    lateinit var receiverService: ReceiverService

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        AndroidInjection.inject(this, context)
        val appName = context?.resources?.getString(R.string.app_name)

        compositeDisposable.add(
            receiverService.getCurrentReleasedTV()
                .subscribe {
                    onSuccessGetTV(context, appName, it)
                }
        )


    }

    private fun onSuccessGetTV(context: Context?, appName: String?, tvSeriesResult: TVSeriesResult?) {
        displayReminder(
            context,
            appName,
            context?.getString(R.string.release_reminder_message)?.let { String.format(it, tvSeriesResult?.name) },
            tvSeriesResult,
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID,
            "RELEASE_REMINDER"
        )

        compositeDisposable.dispose()
    }

    override fun notificationIntent(context: Context?, intent: Intent?): Intent {
        val bundleExtras = intent?.getBundleExtra("bundle")
        val tvSeriesResult = bundleExtras?.getParcelable<TVSeriesResult>("tv")
        return TVSeriesDetailActivity.generateIntent(context, tvSeriesResult)
    }

    override fun addExtraToIntent(result: TVSeriesResult?): Bundle {
        val bundleExtras = Bundle()
        bundleExtras.putParcelable("tv", result)
        return bundleExtras
    }
}