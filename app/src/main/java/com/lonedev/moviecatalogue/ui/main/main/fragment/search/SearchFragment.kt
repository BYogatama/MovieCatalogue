/*
 * Created by Bagus Yogatama on 7/18/19 1:22 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 1:22 AM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseFragment
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.SearchResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.ui.adapter.SearchAdapter
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: SearchViewModel

    @BindView(R.id.rec_search_results)
    lateinit var recSearchResults: RecyclerView

    @BindView(R.id.search_movie_tv)
    lateinit var searchMovieTV: SearchView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.greet)
    lateinit var greetText: TextView

    lateinit var parent: AppCompatActivity

    lateinit var searchAdapter: SearchAdapter

    override fun layoutResources(): Int {
        return R.layout.fragment_search
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)

        searchMovieTV.setOnQueryTextListener(this)

        setupRecyclerView()

        viewModel.onSuccessSearch()
            .observe(this, Observer {
                searchAdapter.searchResults = it
                recSearchResults.visibility = View.VISIBLE
                greetText.visibility = View.GONE
                searchAdapter.notifyDataSetChanged()
            })

        viewModel.onErrorSearch()
            .observe(this, Observer {
                displaySnackBar(it)
            })
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter(getBaseActivity(), requestManager)
        searchAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val result = searchAdapter.searchResults[position]
                if (result.firstAirDate.isNullOrEmpty()) {
                    openMovieDetail(result)
                } else {
                    openTVDetail(result)
                }
            }
        }
        recSearchResults.layoutManager = LinearLayoutManager(getBaseActivity())
        recSearchResults.adapter = searchAdapter
    }

    private fun openMovieDetail(result: SearchResult) {
        val movie = MovieResult(
            result.id,
            0,
            result.voteAverage,
            result.title,
            0.0,
            result.posterPath,
            "",
            result.backdropPath,
            result.overview,
            result.releaseDate
        )
        val detail = Intent(getBaseActivity(), MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(detail)
    }

    private fun openTVDetail(result: SearchResult) {
        val tv = TVSeriesResult(
            result.id,
            "",
            result.name,
            0.0,
            0,
            result.firstAirDate,
            result.backdropPath,
            result.voteAverage,
            result.overview,
            result.posterPath
        )
        val detail = Intent(getBaseActivity(), TVSeriesDetailActivity::class.java).apply {
            putExtra("tv", tv)
        }
        startActivity(detail)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.search(query)
        root_view.hideKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}