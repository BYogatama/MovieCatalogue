/*
 * Created by Bagus Yogatama on 7/20/19 7:47 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 1:19 AM
 */

package com.lonedev.favouriteapp.ui.favorite.fragment.tv

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
import com.lonedev.favouriteapp.R
import com.lonedev.favouriteapp.ui.adapter.ListAdapter
import com.lonedev.favouriteapp.base.BaseFragment
import com.lonedev.favouriteapp.data.models.TVSeriesResult
import com.lonedev.favouriteapp.ui.details.tvseries.TVSeriesDetailActivity
import com.lonedev.favouriteapp.utils.OnItemClickListener
import com.lonedev.favouriteapp.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie_favorites.*
import javax.inject.Inject

class FavouritesTVFragment : BaseFragment() {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: FavouritesTVViewModel
    private lateinit var listAdapter: ListAdapter<TVSeriesResult>

    @BindView(R.id.list_item)
    lateinit var recMovies: RecyclerView

    override fun layoutResources(): Int {
        return R.layout.fragment_movie_favorites
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(FavouritesTVViewModel::class.java)

        listAdapter = ListAdapter(getBaseActivity())

        viewModel.onGetFavoritesResult().observe(this,
            Observer<List<TVSeriesResult>> {
                listAdapter.movies = it
                setupRecyclerView(2)
            })
        viewModel.onGetFavoritesError().observe(this, Observer<String> {
            displaySnackBar(it)
        })

        viewModel.getFavorites()

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

    private fun getMovies() {
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

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

}
