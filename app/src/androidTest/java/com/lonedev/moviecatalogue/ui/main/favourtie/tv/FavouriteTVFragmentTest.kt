/*
 * Created by Bagus Yogatama on 8/25/19 11:26 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/25/19 11:26 AM
 */

package com.lonedev.moviecatalogue.ui.main.favourtie.tv

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.main.main.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FavouriteTVFragmentTest {

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun a_openTVFavourites() {
        Espresso.onView(
            Matchers.anyOf(
                ViewMatchers.withText(R.string.favorite),
                ViewMatchers.withId(R.id.action_favorite)
            )
        ).perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription(R.string.tv_series),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.tabs),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())
    }

    @Test
    fun b_openFavouriteTVDetail() {
        a_openTVFavourites()
        Espresso.onView(ViewMatchers.withId(R.id.favourties_tv_list)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<ListAdapter.MovieViewHolder<TVSeriesResult>>(
                    0,
                    ViewActions.click()
                )
        )
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