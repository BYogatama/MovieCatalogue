/*
 * Created by Bagus Yogatama on 6/29/19 8:07 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 8:07 AM
 */

package com.lonedev.moviecatalogue.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class VideoResult(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("key")
    val key: String? = null
)