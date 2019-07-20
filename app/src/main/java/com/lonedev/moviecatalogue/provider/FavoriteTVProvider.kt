/*
 * Created by Bagus Yogatama on 7/9/19 8:16 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/9/19 8:16 AM
 */

package com.lonedev.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.lonedev.moviecatalogue.BuildConfig
import com.lonedev.moviecatalogue.data.local.AppDatabase
import com.lonedev.moviecatalogue.data.local.dao.FavouritesDao
import com.lonedev.moviecatalogue.data.models.Favourites

class FavoriteTVProvider : ContentProvider() {

    companion object {
        private val AUTHORITIES = "com.lonedev.moviecatalogue.provider.tvs"
        var URI_FAVOURTIES = Uri.parse(
            "content://$AUTHORITIES/favourites"
        )

    }

    private val CODE_TV_DIR = 101
    private val CODE_TV_ITEM = 102

    lateinit var appDatabase: AppDatabase
    lateinit var favouritesDao: FavouritesDao

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, "favourites", CODE_TV_DIR)
        addURI(AUTHORITIES, "favourites/#", CODE_TV_ITEM)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            CODE_TV_DIR -> {
                val context = context ?: return null
                val id = favouritesDao.saveFavouriteCursor(Favourites.fromContentValues(values))
                context.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            CODE_TV_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val context = context ?: return null

        return when (uriMatcher.match(uri)) {
            CODE_TV_DIR -> {
                val cursor: Cursor = favouritesDao.getFavouriteTVSeriesCursor()
                cursor.setNotificationUri(context.contentResolver, uri)
                cursor
            }
            CODE_TV_ITEM -> {
                val cursor = favouritesDao.getFavoritedTVCursor(ContentUris.parseId(uri))
                cursor.setNotificationUri(context.contentResolver, uri)
                cursor
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")

        }
    }

    override fun onCreate(): Boolean {
        appDatabase = Room.databaseBuilder(context!!, AppDatabase::class.java, BuildConfig.DB_NAME).build()
        favouritesDao = appDatabase.favouritesDao()
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (uriMatcher.match(uri)) {
            CODE_TV_ITEM -> {
                val count = favouritesDao.deleteFavouriteCursor(ContentUris.parseId(uri))
                context?.contentResolver?.notifyChange(uri, null)
                count
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {

        return when (uriMatcher.match(uri)) {
            CODE_TV_DIR -> "vnd.android.cursor.dir/${AUTHORITIES}.favourites"
            CODE_TV_ITEM -> "vnd.android.cursor.item/${AUTHORITIES}.favourites"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

}