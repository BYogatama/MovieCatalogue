/*
 * Created by Bagus Yogatama on 8/28/19 9:13 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/28/19 9:13 PM
 */

package com.lonedev.moviecatalogue.base.shceduler

import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: TestScheduler) : BaseSchedulerProvider {
    override fun computation() = scheduler
    override fun ui() = scheduler
    override fun io() = scheduler
}