/*
 * Created by Bagus Yogatama on 6/22/19 6:31 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/22/19 6:14 PM
 */

package com.lonedev.moviecatalogue.data.models

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