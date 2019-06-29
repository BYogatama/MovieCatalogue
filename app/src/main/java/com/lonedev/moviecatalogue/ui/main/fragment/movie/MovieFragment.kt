/*
 * Created by Bagus Yogatama on 6/28/19 10:39 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 8:44 PM
 */

package com.lonedev.moviecatalogue.ui.main.fragment.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseFragment
import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.main.details.MovieDetailActivity
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.movie_fragment.*
import javax.inject.Inject

class MovieFragment : BaseFragment() {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: MovieViewModel
    private lateinit var listAdapter: ListAdapter<MovieResult>

    @BindView(R.id.list_item)
    lateinit var recMovies: RecyclerView

    override fun layoutResources(): Int {
        return R.layout.movie_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        listAdapter = ListAdapter(getBaseActivity())

        getMovies()
    }

    private fun setupRecyclerView(gridCount : Int) {
        listAdapter.requestManager = requestManager

        listAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                openMovieDetail(position)
            }
        }

        recMovies.layoutManager = GridLayoutManager(getBaseActivity(), gridCount)
        recMovies.adapter = listAdapter

        recMovies.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    private fun openMovieDetail(position: Int) {
        val detail = Intent(getBaseActivity(), MovieDetailActivity::class.java)
        detail.putExtra("movie", listAdapter.movies.results[position])
        startActivity(detail)
    }

    private fun getMovies() {
        viewModel.loadMovies()

        viewModel.movieResult().observe(this,
            Observer<Movie<MovieResult>> {
                listAdapter.movies = it
                setupRecyclerView(2) // Default Grid Count is 2
            })

        viewModel.movieError().observe(this, Observer<String> {
            displaySnackBar(it)
        })
    }

    override fun onDestroyView() {
        viewModel.disposeElements()
        super.onDestroyView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupRecyclerView(3)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setupRecyclerView(2)
        }
    }
}