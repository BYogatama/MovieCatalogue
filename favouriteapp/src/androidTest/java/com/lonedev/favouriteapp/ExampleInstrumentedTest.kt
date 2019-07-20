/*
 * Created by Bagus Yogatama on 7/20/19 7:37 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:37 PM
 */

package com.lonedev.favouriteapp

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.lonedev.favouriteapp", appContext.packageName)
    }
}
