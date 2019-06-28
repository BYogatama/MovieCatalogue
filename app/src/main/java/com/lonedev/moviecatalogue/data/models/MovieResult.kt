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
    val id: Int? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readString(),
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readString(),
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(voteCount)
        writeValue(id)
        writeValue(video)
        writeValue(voteAverage)
        writeString(title)
        writeValue(popularity)
        writeString(posterPath)
        writeString(originalTitle)
        writeList(genreIds)
        writeString(backdropPath)
        writeValue(adult)
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