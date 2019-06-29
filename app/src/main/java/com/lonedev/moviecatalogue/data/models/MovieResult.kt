/*
 * Created by Bagus Yogatama on 6/28/19 7:39 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 7:39 PM
 */

package com.lonedev.moviecatalogue.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class MovieResult(
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readInt(),
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readString(),
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readString(),
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(voteCount)
        writeInt(id)
        writeValue(voteAverage)
        writeString(title)
        writeValue(popularity)
        writeString(posterPath)
        writeString(originalTitle)
        writeList(genreIds)
        writeString(backdropPath)
        writeString(overview)
        writeString(releaseDate)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieResult> = object : Parcelable.Creator<MovieResult> {
            override fun createFromParcel(source: Parcel): MovieResult = MovieResult(source)
            override fun newArray(size: Int): Array<MovieResult?> = arrayOfNulls(size)
        }
    }
}