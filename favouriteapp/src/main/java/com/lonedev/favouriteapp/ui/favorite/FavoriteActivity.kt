/*
 * Created by Bagus Yogatama on 7/20/19 8:08 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:56 PM
 */

package com.lonedev.favouriteapp.ui.favorite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.google.android.material.tabs.TabLayout
import com.lonedev.favouriteapp.R
import com.lonedev.favouriteapp.ui.adapter.ViewPagerAdapter
import com.lonedev.favouriteapp.base.BaseActivity
import com.lonedev.favouriteapp.ui.favorite.fragment.tv.FavouritesTVFragment
import com.lonedev.favouriteapp.ui.favorite.fragment.movie.FavouritesMoviesFragment
import kotlinx.android.synthetic.main.activity_favorite.*


class FavoriteActivity : BaseActivity() {

    @BindView(R.id.tabs)
    lateinit var mTabLayout: TabLayout
    @BindView(R.id.viewpager)
    lateinit var mViewPager: ViewPager

    override fun layoutResource(): Int {
        return R.layout.activity_favorite
    }

    override fun getRootView(): View {
        return this.findViewById(R.id.root_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.favorite)

        val mViewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.POSITION_UNCHANGED
        )
        mViewPagerAdapter.addFragment(FavouritesMoviesFragment(), getString(R.string.movies))
        mViewPagerAdapter.addFragment(FavouritesTVFragment(), getString(R.string.tv_series))

        mViewPager.adapter = mViewPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)

    }
}