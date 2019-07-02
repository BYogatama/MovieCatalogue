/*
 * Created by Bagus Yogatama on 6/29/19 9:35 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 9:35 PM
 */

package com.lonedev.moviecatalogue.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntTypeConverter {

    @TypeConverter
    fun listOfIntToString(integers: List<Int>): String {
        return Gson().toJson(integers)
    }

    @TypeConverter
    fun stringToListOfInt(string: String): List<Int> {
        val collectionType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(string, collectionType)
    }

}