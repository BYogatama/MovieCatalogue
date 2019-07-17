/*
 * Created by Bagus Yogatama on 7/4/19 11:08 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/4/19 11:08 PM
 */

package com.lonedev.moviecatalogue.ui.widget.services

import android.content.Intent
import android.widget.RemoteViewsService
import com.lonedev.moviecatalogue.ui.widget.factory.TheMovieWidgetFactory

class TheMovieWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return TheMovieWidgetFactory(applicationContext)
    }
}