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
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseFragment
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.adapter.TVListAdapter
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import com.paginate.Paginate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

class TVSeriesFragment : BaseFragment(), Paginate.Callbacks {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: TVSeriesViewModel
    private lateinit var listAdapter: TVListAdapter

    private var page = 1
    private var isLoading = false
    private var hasLoadedAllItems = false

    @BindView(R.id.list_item)
    lateinit var recMovies: RecyclerView

    private val compositeDisposable = CompositeDisposable()

    override fun layoutResources(): Int {
        return R.layout.fragment_tvseries
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(TVSeriesViewModel::class.java)


        listAdapter = TVListAdapter(getBaseActivity())

        getTVSeries()
    }

    private fun setupRecyclerView(gridCount: Int) {
        listAdapter.requestManager = requestManager

        listAdapter.onItemClickListener = ({ tvSeries ->
            openMovieDetail(tvSeries)
        })

        recMovies.layoutManager = GridLayoutManager(getBaseActivity(), gridCount)
        recMovies.adapter = listAdapter

        Paginate.with(recMovies, this).build()

        recMovies.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    private fun openMovieDetail(tvSeries: TVSeriesResult) {
        val detail = Intent(getBaseActivity(), TVSeriesDetailActivity::class.java)
        detail.putExtra("tv", tvSeries)
        startActivity(detail)
    }

    private fun getTVSeries() {
        viewModel.getTVSeries(page).subscribe()
        viewModel.onGetTVSeries().observe(this,
            Observer<PagedList<TVSeriesResult>> {
                listAdapter.submitList(it)
                setupRecyclerView(2)
            })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupRecyclerView(3)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupRecyclerView(2)
        }
    }

    override fun onLoadMore() {
        if (!isLoading) {
            isLoading = true
            compositeDisposable.add(
                viewModel.getTVSeries(page++)
                    .subscribe {
                        isLoading = false
                    }
            )
        }
    }

    override fun isLoading(): Boolean = isLoading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        super.onDestroy()
    }

}