/*
 * Created by Bagus Yogatama on 8/25/19 12:53 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/25/19 12:53 AM
 */

package com.lonedev.moviecatalogue.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import junit.framework.Assert.assertNotNull


class RecyclerViewItemMoreThan(private val expectedOutput: Int) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertNotNull(adapter)
        if (adapter != null) {
            assert(adapter.itemCount > expectedOutput)
        }
    }
}