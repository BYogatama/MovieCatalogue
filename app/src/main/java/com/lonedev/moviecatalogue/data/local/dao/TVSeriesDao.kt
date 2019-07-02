/*
 * Created by Bagus Yogatama on 6/29/19 2:34 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 2:34 PM
 */

package com.lonedev.moviecatalogue.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.Single

@Dao
interface TVSeriesDao {
    @Query("SELECT * FROM tvseries")
    fun getTVSeries(): Single<List<TVSeriesResult>>

    @Query("SELECT * FROM tvseries WHERE id=:tvSeriesId")
    fun getTVSeries(tvSeriesId: Int): TVSeriesResult?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTVSeries(tvSeriesResult: TVSeriesResult)
}