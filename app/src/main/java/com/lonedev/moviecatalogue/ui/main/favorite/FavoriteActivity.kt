/*
 * Created by Bagus Yogatama on 7/2/19 2:24 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:24 PM
 */

package com.lonedev.moviecatalogue.ui.main.favorite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.google.android.material.tabs.TabLayout
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.ui.adapter.ViewPagerAdapter
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.movie.FavouritesMoviesFragment
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.tv.FavouritesTVFragment
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.POSITION_UNCHANGED)
        mViewPagerAdapter.addFragment(FavouritesMoviesFragment(), getString(R.string.movies))
        mViewPagerAdapter.addFragment(FavouritesTVFragment(), getString(R.string.tv_series))

        mViewPager.adapter = mViewPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}