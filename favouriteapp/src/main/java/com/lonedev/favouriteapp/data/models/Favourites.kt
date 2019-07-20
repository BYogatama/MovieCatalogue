/*
 * Created by Bagus Yogatama on 7/20/19 7:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 1:19 AM
 */

package com.lonedev.favouriteapp.data.models

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourites")
class Favourites(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var favId: Int,
    var type: String
) {

    constructor() : this(0, 0, "")

    object Type {
        var MOVIES = "MOVIES"
        var TVSERIES = "TVSERIES"
    }

    companion object {
        fun fromContentValues(values: ContentValues?): Favourites {
            val fav = Favourites()

            if (values != null) {
                if (values.containsKey("id")) {
                    fav.id = values.getAsInteger("id")
                }
                if (values.containsKey("favId")) {
                    fav.favId = values.getAsInteger("favId")
                }
                if (values.containsKey("type")) {
                    fav.type = values.getAsString("type")
                }
            }

            return fav
        }
    }

}