/*
 * Created by Bagus Yogatama on 7/2/19 2:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:57 PM
 */

package com.lonedev.moviecatalogue.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter constructor(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {

    private var fragments: MutableList<Fragment> = ArrayList()
    private var fragmentTitles: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]
    }
}