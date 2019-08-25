/*
 * Created by Bagus Yogatama on 7/14/19 3:12 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/14/19 3:12 PM
 */

package com.lonedev.moviecatalogue.ui.main.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity() {

    override fun layoutResource(): Int {
        return R.layout.activity_settings
    }

    override fun getRootView(): View {
        return root_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)


        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SettingsFragment(), "settings")
            .commitNow()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}