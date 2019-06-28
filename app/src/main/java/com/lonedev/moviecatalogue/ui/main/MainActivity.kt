/*
 * Created by Bagus Yogatama on 6/22/19 6:31 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/22/19 2:31 PM
 */

package com.lonedev.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.ui.main.fragment.movie.MovieFragment
import com.lonedev.moviecatalogue.ui.main.fragment.tv.TVSeriesFragment

class MainActivity : BaseActivity() {

    @BindView(R.id.bottom_navigation)
    lateinit var bottomNavigation: BottomNavigationView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_movies -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieFragment(), "movies")
                    .commitNow()
                title = "Movie"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tv -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TVSeriesFragment(), "tv_series")
                    .commitNow()
                title = "TV Series"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ButterKnife.bind(this)

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.navigation_movies

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_setting) {
            val langSetting = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(langSetting)
        }
        return super.onOptionsItemSelected(item)
    }
}
