/*
 * Created by Bagus Yogatama on 6/29/19 9:39 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 9:39 PM
 */

package com.lonedev.moviecatalogue.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringTypeConverter {
    @TypeConverter
    fun listOfStringToString(integers: List<String>): String {
        return Gson().toJson(integers)
    }

    @TypeConverter
    fun stringToListOfString(string: String): List<String> {
        val collectionType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, collectionType)
    }

}