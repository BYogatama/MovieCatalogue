/*
 * Created by Bagus Yogatama on 8/28/19 9:12 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/28/19 9:12 PM
 */

package com.lonedev.moviecatalogue.base.shceduler

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}