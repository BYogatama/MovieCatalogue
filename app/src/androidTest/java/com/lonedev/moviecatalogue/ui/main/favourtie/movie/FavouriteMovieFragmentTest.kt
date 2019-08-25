/*
 * Created by Bagus Yogatama on 8/25/19 11:26 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/25/19 11:25 AM
 */

package com.lonedev.moviecatalogue.ui.main.favourtie.movie

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.main.main.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import org.hamcrest.TypeSafeMatcher
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FavouriteMovieFragmentTest {

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun a_openFavourites() {
        onView(
            anyOf(
                withText(R.string.favorite),
                withId(R.id.action_favorite)
            )
        ).perform(click())

        onView(
            allOf(
                withContentDescription(R.string.movies),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        ).perform(click())
    }

    @Test
    fun b_openFavouriteMovieDetail() {
        a_openFavourites()
        onView(withId(R.id.favourties_movie_list)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<ListAdapter.MovieViewHolder<MovieResult>>(
                    0,
                    click()
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