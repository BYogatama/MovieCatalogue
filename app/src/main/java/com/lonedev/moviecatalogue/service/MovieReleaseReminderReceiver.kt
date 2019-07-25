/*
 * Created by Bagus Yogatama on 7/15/19 11:04 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/15/19 11:04 PM
 */

package com.lonedev.moviecatalogue.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseBroadcastReceiver
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieReleaseReminderReceiver :
    BaseBroadcastReceiver<MovieResult>() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var receiverService: ReceiverService

    companion object {
        const val NOTIFICATION_ID: Int = 201
        const val NOTIFICATION_CHANNEL_ID: String = "200"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        AndroidInjection.inject(this, context)

        val appName = context?.resources?.getString(R.string.app_name)

        compositeDisposable.add(
            receiverService.getCurrentReleasedMovie().subscribe {
                onSuccessGetMovie(context, appName, it)
            }
        )


    }

    private fun onSuccessGetMovie(context: Context?, appName: String?, movieResult: MovieResult?) {
        displayReminder(
            context,
            appName,
            context?.getString(R.string.release_reminder_message)?.let { String.format(it, movieResult?.title) },
            movieResult,
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID,
            "RELEASE_REMINDER"
        )

        compositeDisposable.dispose()
    }

    override fun notificationIntent(context: Context?, intent: Intent?): Intent {
        val bundleExtras = intent?.getBundleExtra("bundle")
        val movieResult: MovieResult? = bundleExtras?.getParcelable("movie")
        return MovieDetailActivity.generateIntent(context, movieResult)
    }


    override fun addExtraToIntent(result: MovieResult?): Bundle {
        val bundleExtras = Bundle()
        bundleExtras.putParcelable("movie", result)
        return bundleExtras
    }


}