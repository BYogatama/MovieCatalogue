/*
 * Created by Bagus Yogatama on 7/20/19 7:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 10:49 PM
 */

package com.lonedev.favouriteapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Movie<T : Parcelable>(

    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("results")
    var results: List<T>

)