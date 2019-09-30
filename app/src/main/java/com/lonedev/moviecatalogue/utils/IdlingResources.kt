/*
 * Created by Bagus Yogatama on 9/30/19 8:05 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/30/19 8:05 PM
 */

package com.lonedev.moviecatalogue.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource



object IdlingResources {

    private const val RESOURCE = "GLOBAL"

    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource {
        return espressoTestIdlingResource
    }

}