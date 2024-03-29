/*
 * Created by Bagus Yogatama on 7/2/19 2:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/30/19 1:55 PM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.movie

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
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.ui.adapter.MovieListAdapter
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import com.paginate.Paginate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

class MovieFragment : BaseFragment(), Paginate.Callbacks {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: MovieViewModel
    private lateinit var listAdapter: MovieListAdapter

    private var page = 1
    private var isLoading = false
    private var hasLoadedAllItems = false

    private val compositeDisposable = CompositeDisposable()

    @BindView(R.id.list_item)
    lateinit var recMovies: RecyclerView

    override fun layoutResources(): Int {
        return R.layout.fragment_movie
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        listAdapter = MovieListAdapter(getBaseActivity())
        setupRecyclerView(2)

        getMovies()
    }

    private fun setupRecyclerView(gridCount: Int) {
        listAdapter.requestManager = requestManager

        listAdapter.onItemClickListener = ({ movie ->
            openMovieDetail(movie)
        })

        recMovies.layoutManager = GridLayoutManager(getBaseActivity(), gridCount)
        recMovies.adapter = listAdapter

        Paginate.with(recMovies, this).build()

        recMovies.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    private fun openMovieDetail(movie: MovieResult) {
        val detail = Intent(getBaseActivity(), MovieDetailActivity::class.java)
        detail.putExtra("movie", movie)
        startActivity(detail)
    }

    private fun getMovies() {
        viewModel.getMovies(page).subscribe()
        viewModel.onGetMovies().observe(this,
            Observer<PagedList<MovieResult>> {
                listAdapter.submitList(it)
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
                viewModel.getMovies(page++)
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