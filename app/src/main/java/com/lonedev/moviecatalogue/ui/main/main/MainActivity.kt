/*
 * Created by Bagus Yogatama on 7/2/19 3:08 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:23 PM
 */

package com.lonedev.moviecatalogue.ui.main.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.firebase.jobdispatcher.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.FavoriteActivity
import com.lonedev.moviecatalogue.ui.main.main.fragment.movie.MovieFragment
import com.lonedev.moviecatalogue.ui.main.main.fragment.tv.TVSeriesFragment
import kotlinx.android.synthetic.main.activity_detail_movies.*


class MainActivity : BaseActivity() {

    @BindView(R.id.bottom_navigation)
    lateinit var bottomNavigation: BottomNavigationView

    lateinit var jobDispatcher: FirebaseJobDispatcher

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_movies -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieFragment(), "movies")
                    .commitNow()
                title = resources.getString(R.string.movies)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tv -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TVSeriesFragment(), "tv_series")
                    .commitNow()
                title = resources.getString(R.string.tv_series)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    override fun getRootView(): View {
        return findViewById(R.id.parent_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

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
        } else if (item?.itemId == R.id.action_favorite) {
            val favorite = Intent(this, FavoriteActivity::class.java)
            startActivity(favorite)
        }
        return super.onOptionsItemSelected(item)
    }


}