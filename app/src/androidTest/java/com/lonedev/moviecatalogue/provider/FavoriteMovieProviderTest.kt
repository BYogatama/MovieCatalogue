/*
 * Created by Bagus Yogatama on 7/4/19 9:22 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/4/19 9:22 PM
 */

package com.lonedev.moviecatalogue.provider

import android.content.ContentResolver
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.lonedev.moviecatalogue.data.local.AppDatabase
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class FavoriteMovieProviderTest {

    lateinit var contentResolver: ContentResolver

    @Before
    fun setUpDatabase() {
        Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun getMovies() {
        val cursor = contentResolver.query(
            FavoriteMovieProvider.URI_FAVOURTIES
            , null, null, null, null, null
        )
        assertThat(cursor, notNullValue())
        cursor.moveToNext()
        val String = cursor.getString(8)
        print(String)
        cursor.close()
    }
}