/*
 * Created by Bagus Yogatama on 9/22/19 5:47 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 5:47 PM
 */

package com.lonedev.moviecatalogue.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context) {
    fun isConnectedToInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo = cm.activeNetworkInfo
        return activeNetwork.isConnected
    }
}