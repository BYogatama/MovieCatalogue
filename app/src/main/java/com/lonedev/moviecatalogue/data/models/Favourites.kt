/*
 * Created by Bagus Yogatama on 6/29/19 2:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 2:23 PM
 */

package com.lonedev.moviecatalogue.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
class Favourites(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var favId: Int,
    var type: String
){
    object  Type {
        var MOVIES = "MOVIES"
        var TVSERIES = "TVSERIES"
    }
}