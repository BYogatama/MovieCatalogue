/*
 * Created by Bagus Yogatama on 7/14/19 11:50 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/14/19 11:50 AM
 */

package com.lonedev.moviecatalogue.ui.widget.services

import android.content.Intent
import android.widget.RemoteViewsService
import com.lonedev.moviecatalogue.ui.widget.factory.TheTVWidgetFactory

class TheTVWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return TheTVWidgetFactory(this)
    }
}