/*
 * Created by Bagus Yogatama on 6/29/19 8:06 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 8:06 AM
 */

package com.lonedev.moviecatalogue.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Video<T>(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("results")
    val results: List<T>
)