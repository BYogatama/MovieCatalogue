/*
 * Created by Bagus Yogatama on 7/4/19 11:10 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/4/19 11:10 PM
 */

package com.lonedev.moviecatalogue.ui.widget.factory

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.provider.FavoriteMovieProvider
import com.lonedev.moviecatalogue.utils.Constant


class TheMovieWidgetFactory constructor(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var cursor: Cursor? = null
    private lateinit var contentResolver: ContentResolver

    override fun onCreate() {
        contentResolver = context.contentResolver
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return if (cursor!!.moveToPosition(position)) cursor!!.getLong(0) else position.toLong()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        try {
            cursor = contentResolver.query(
                FavoriteMovieProvider.URI_FAVOURTIES,
                null, null, null, null, null
            )
        } finally {
            Binder.restoreCallingIdentity(identityToken)
        }
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget)

        val movie = getItem(position)

        val imgUrl = "${Constant.IMAGE_URL}${Constant.W300}${movie?.posterPath}"

        val fillIntent = Intent()
        val bundleExtras = Bundle().apply {
            putParcelable("movie", movie)
        }

        fillIntent.putExtra("movie", bundleExtras)

        val bitmap = Glide.with(context.applicationContext)
            .asBitmap()
            .load(imgUrl)
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()

        remoteViews.setImageViewBitmap(R.id.movie_poster, bitmap)
        remoteViews.setTextViewText(R.id.movie_title, movie?.title)
        remoteViews.setOnClickFillInIntent(R.id.movie_poster, fillIntent)

        return remoteViews
    }

    override fun getCount(): Int {
        return if (cursor != null) cursor!!.count else 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        cursor?.close()
    }

    private fun getItem(position: Int): MovieResult? {
        cursor?.moveToPosition(position)
        return cursor?.let { MovieResult(it) }
    }

}