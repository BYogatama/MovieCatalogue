/*
 * Created by Bagus Yogatama on 7/20/19 7:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 4:20 PM
 */

package com.lonedev.favouriteapp.data.models

import com.google.gson.annotations.SerializedName



class Video<T>(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("results")
    val results: List<T>
)