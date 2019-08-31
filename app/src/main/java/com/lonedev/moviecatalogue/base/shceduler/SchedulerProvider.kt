/*
 * Created by Bagus Yogatama on 8/28/19 9:12 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/28/19 9:12 PM
 */

package com.lonedev.moviecatalogue.base.shceduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun ui() = AndroidSchedulers.mainThread()
    override fun io() = Schedulers.io()
}