/*
 * Created by Bagus Yogatama on 8/25/19 2:00 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/25/19 1:38 AM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.tv

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.main.main.MainActivity
import com.lonedev.moviecatalogue.utils.RecyclerViewItemMoreThan
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TVSeriesFragmentTest {

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun selectMovieFragment() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.navigation_tv), withContentDescription(R.string.tv_series),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(ViewActions.click())
    }

    @Test
    fun a_getTVSeries() {
        onView(withId(R.id.progress)).check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withId(R.id.list_item)).check(matches(isDisplayed()))
        onView(withId(R.id.list_item)).check(RecyclerViewItemMoreThan(10))
    }

    @Test
    fun b_openTVDetail() {
        onView(withId(R.id.list_item)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<ListAdapter.MovieViewHolder<MovieResult>>(0, ViewActions.click())
        )
    }

    @Test
    fun c_addToFavourites() {
        onView(withId(R.id.list_item)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<ListAdapter.MovieViewHolder<MovieResult>>(0, ViewActions.click())
        )
        onView(withId(R.id.fab_favorites)).perform(ViewActions.click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}