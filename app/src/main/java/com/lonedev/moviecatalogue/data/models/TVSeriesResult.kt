/*
 * Created by Bagus Yogatama on 6/24/19 5:45 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/24/19 5:45 PM
 */

package com.lonedev.moviecatalogue.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvseries")
class TVSeriesResult(
    @PrimaryKey
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("origin_country")
    val originCountry: List<String>? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val posterPath: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readString(),
        source.readValue(Double::class.java.classLoader) as Double?,
        source.createStringArrayList(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString()!!,
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readString(),
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(originalName)
        writeList(genreIds)
        writeString(name)
        writeValue(popularity)
        writeStringList(originCountry)
        writeValue(voteCount)
        writeString(firstAirDate)
        writeString(backdropPath)
        writeValue(voteAverage)
        writeString(overview)
        writeString(posterPath)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TVSeriesResult> = object : Parcelable.Creator<TVSeriesResult> {
            override fun createFromParcel(source: Parcel): TVSeriesResult = TVSeriesResult(source)
            override fun newArray(size: Int): Array<TVSeriesResult?> = arrayOfNulls(size)
        }
    }
}