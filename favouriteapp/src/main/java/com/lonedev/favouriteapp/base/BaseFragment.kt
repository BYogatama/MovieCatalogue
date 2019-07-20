/*
 * Created by Bagus Yogatama on 7/20/19 7:51 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 10:49 PM
 */

package com.lonedev.favouriteapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment

/**
 * Used for base of fragment that extends DaggerFragment, for every repeatable declaration in fragment
 * can be put here, so it only declared once.
 */
abstract class BaseFragment : DaggerFragment() {

    private lateinit var unbinder: Unbinder
    private lateinit var activity: AppCompatActivity

    @LayoutRes
    protected abstract fun layoutResources(): Int

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResources(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    fun getBaseActivity(): Context {
        return activity.applicationContext
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    protected fun displaySnackBar(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

}