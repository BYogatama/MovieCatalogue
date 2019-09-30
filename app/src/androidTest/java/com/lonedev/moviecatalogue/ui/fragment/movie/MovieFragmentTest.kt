/*
 * Created by Bagus Yogatama on 8/25/19 1:01 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/25/19 1:01 AM
 */

package com.lonedev.moviecatalogue.ui.fragment.movie

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.ui.adapter.MovieListAdapter
import com.lonedev.moviecatalogue.ui.main.main.MainActivity
import com.lonedev.moviecatalogue.utils.IdlingResources
import com.lonedev.moviecatalogue.utils.RecyclerViewItemMoreThan
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.*
import org.junit.runners.MethodSorters
import kotlin.random.Random


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MovieFragmentTest {

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(IdlingResources.getIdlingResource())
    }

    @Before
    fun selectMovieFragment() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.navigation_movies), withContentDescription(R.string.movies),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
    }

    @Test
    fun a_getMovies() {
        onView(withId(R.id.list_item)).check(matches(isDisplayed()))
        onView(withId(R.id.list_item)).check(RecyclerViewItemMoreThan(10))
    }

    @Test
    fun b_openMovieDetail() {
        val index = Random.nextInt(1, 10)
        onView(withId(R.id.list_item))
            .perform(
                RecyclerViewActions.scrollToPosition<MovieListAdapter.MovieViewHolder>(index)
            )
        onView(withId(R.id.list_item))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<MovieListAdapter.MovieViewHolder>(index, click())
            )
    }

    @Test
    fun c_addToFavourites() {
        val index = Random.nextInt(1, 10)
        onView(withId(R.id.list_item))
            .perform(
                RecyclerViewActions.scrollToPosition<MovieListAdapter.MovieViewHolder>(index)
            )
        onView(withId(R.id.list_item))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<MovieListAdapter.MovieViewHolder>(index, click())
            )
        onView(withId(R.id.fab_favorites)).perform(click())
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

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResources.getIdlingResource())
    }

}