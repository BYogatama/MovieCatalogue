/*
 * Created by Bagus Yogatama on 8/25/19 11:26 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/25/19 11:25 AM
 */

package com.lonedev.moviecatalogue.ui.main.favourtie.movie

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.local.dao.FavouritesDao
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.models.Favourites
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.db.di.DaggerInstrumentTestComponent
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.adapter.MovieListAdapter
import com.lonedev.moviecatalogue.ui.main.main.MainActivity
import com.lonedev.moviecatalogue.utils.IdlingResources
import io.reactivex.schedulers.Schedulers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import org.hamcrest.TypeSafeMatcher
import org.junit.*
import org.junit.runners.MethodSorters
import javax.inject.Inject
import kotlin.random.Random

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FavouriteMovieFragmentTest {

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(IdlingResources.getIdlingResource())
    }

    @Test
    fun a_openFavourites() {
        onView(
            anyOf(
                withText(R.string.favorite),
                withId(R.id.action_favorite)
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

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResources.getIdlingResource())
    }

}