/*
 * Created by Bagus Yogatama on 7/19/19 10:00 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/19/19 10:00 AM
 */

package com.lonedev.moviecatalogue.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class SearchResult(
    val id: Int = -1,
    val name: String = "",
    val title: String = "",
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("vote_average")
    val voteAverage: Double? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(title)
        writeString(overview)
        writeString(posterPath)
        writeString(firstAirDate)
        writeString(releaseDate)
        writeString(backdropPath)
        writeValue(voteAverage)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SearchResult> = object : Parcelable.Creator<SearchResult> {
            override fun createFromParcel(source: Parcel): SearchResult = SearchResult(source)
            override fun newArray(size: Int): Array<SearchResult?> = arrayOfNulls(size)
        }
    }
}