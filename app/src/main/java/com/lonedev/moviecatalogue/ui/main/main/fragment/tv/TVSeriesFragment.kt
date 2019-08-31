/*
 * Created by Bagus Yogatama on 7/2/19 2:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 11:04 PM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.tv

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
import com.lonedev.moviecatalogue.base.shceduler.SchedulerProvider
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.adapter.ListAdapter
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*
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
        return R.layout.fragment_tvseries
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(TVSeriesViewModel::class.java)


        listAdapter = ListAdapter(getBaseActivity())

        getTVSeries()
    }

    private fun setupRecyclerView(gridCount: Int) {
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
        val detail = Intent(getBaseActivity(), TVSeriesDetailActivity::class.java)
        detail.putExtra("tv", listAdapter.movies[position])
        startActivity(detail)
    }

    private fun getTVSeries() {
        viewModel.getTVSeries()

        viewModel.onSuccessGetTVSeries().observe(this,
            Observer<List<TVSeriesResult>> {
                listAdapter.movies = it
                setupRecyclerView(2)
            })

        viewModel.onErrorGetTVSeries().observe(this, Observer<String> {
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
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupRecyclerView(2)
        }
    }

}