/*
 * Created by Bagus Yogatama on 6/28/19 10:40 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 8:48 PM
 */

package com.lonedev.moviecatalogue.ui.main.fragment.tv

import android.content.Intent
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
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.main.details.MovieDetailActivity
import com.lonedev.moviecatalogue.ui.main.details.TVSeriesDetailActivity
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.movie_fragment.*
import javax.inject.Inject

class TVSeriesFragment : BaseFragment() {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: TVSeriesViewModel
    private lateinit var listAdapter: ListAdapter<TVSeriesResult>

    @BindView(R.id.list_item)
    lateinit var recMovies: RecyclerView

    override fun layoutResources(): Int {
        return R.layout.tvshow_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(TVSeriesViewModel::class.java)


        listAdapter = ListAdapter(getBaseActivity())

        getTVSeries()
    }

    private fun setupRecyclerView() {
        listAdapter.requestManager = requestManager

        listAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                openMovieDetail(position)
            }
        }

        recMovies.layoutManager = GridLayoutManager(getBaseActivity(), 2)
        recMovies.adapter = listAdapter

        recMovies.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    private fun openMovieDetail(position: Int) {
        val detail = Intent(getBaseActivity(), TVSeriesDetailActivity::class.java)
        detail.putExtra("tv", listAdapter.movies.results[position])
        startActivity(detail)
    }

    private fun getTVSeries() {
        viewModel.loadTVSeries()

        viewModel.tvSeriesResult().observe(this,
            Observer<Movie<TVSeriesResult>> {
                listAdapter.movies = it
                setupRecyclerView()
            })

        viewModel.tvSeriesError().observe(this, Observer<String> {
            displaySnackBar(it)
        })
    }

    override fun onDestroyView() {
        viewModel.disposeElements()
        super.onDestroyView()
    }

}