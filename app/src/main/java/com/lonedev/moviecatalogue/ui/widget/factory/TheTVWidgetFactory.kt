/*
 * Created by Bagus Yogatama on 7/12/19 9:30 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/12/19 9:30 PM
 */

package com.lonedev.moviecatalogue.ui.widget.factory

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.provider.FavoriteTVProvider
import com.lonedev.moviecatalogue.utils.Constant

class TheTVWidgetFactory constructor(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

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
                FavoriteTVProvider.URI_FAVOURTIES,
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

        val tvs = getItem(position)

        val imgUrl = "${Constant.IMAGE_URL}${Constant.W300}${tvs?.posterPath}"

        val bitmap = Glide.with(context.applicationContext)
            .asBitmap()
            .load(imgUrl)
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()

        remoteViews.setImageViewBitmap(R.id.movie_poster, bitmap)
        remoteViews.setTextViewText(R.id.movie_title, tvs?.name)

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

    private fun getItem(position: Int): TVSeriesResult? {
        cursor?.moveToPosition(position)
        return cursor?.let { TVSeriesResult(it) }
    }

}