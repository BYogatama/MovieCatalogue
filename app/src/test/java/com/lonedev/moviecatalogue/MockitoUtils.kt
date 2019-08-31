/*
 * Created by Bagus Yogatama on 8/31/19 4:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 4:23 PM
 */

package com.lonedev.moviecatalogue

import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
