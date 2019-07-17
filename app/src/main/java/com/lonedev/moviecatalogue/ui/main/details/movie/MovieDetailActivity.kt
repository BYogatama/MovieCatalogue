/*
 * Created by Bagus Yogatama on 6/29/19 9:01 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 10:56 AM
 */

package com.lonedev.moviecatalogue.ui.main.details.movie

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.ui.adapter.VideoAdapter
import com.lonedev.moviecatalogue.ui.widget.TheMovieWidget
import com.lonedev.moviecatalogue.utils.Constant
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_movies.*
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var videoAdapter: VideoAdapter

    @BindView(R.id.movie_trailer)
    lateinit var recVideos: RecyclerView

    lateinit var movie: MovieResult

    lateinit var appWidgetManager: AppWidgetManager


    override fun layoutResource(): Int {
        return R.layout.activity_detail_movies
    }


    override fun getRootView(): View {
        return findViewById(R.id.coordinator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        appWidgetManager = AppWidgetManager.getInstance(this)

        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel::class.java)
        videoAdapter = VideoAdapter(this)

        movie = if (intent.getParcelableExtra<MovieResult>("movie") != null) {
            intent.getParcelableExtra("movie")
        } else {
            val bundle = intent.getBundleExtra("bundle")
            bundle.getParcelable("movie")
        }

        getFavoritedMovies(movie.id)

        requestManager.load(generateImageUrl(movie.backdropPath, Constant.WOriginal))
            .into(movie_poster)

        movie_title.text = movie.title
        displayScore(movie)

        movie_release.text = formatDate(movie.releaseDate)

        movie_overview.text = movie.overview

        getVideos(movieId = movie.id)

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun displayScore(movie: MovieResult) {
        val score: Int = movie.voteAverage?.times(10)!!.toInt()
        val scoreBackground = ContextCompat.getDrawable(this, R.drawable.score_frame)


        when {
            score <= 50 -> scoreBackground?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(this, R.color.red),
                PorterDuff.Mode.SRC_ATOP
            )
            score <= 70 -> scoreBackground?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(this, R.color.yellow),
                PorterDuff.Mode.SRC_ATOP
            )
            else -> scoreBackground?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(this, R.color.green),
                PorterDuff.Mode.SRC_ATOP
            )
        }

        movie_score_frame.background = scoreBackground
        movie_score.text = score.toString()
    }

    private fun setupRecyclerView() {
        videoAdapter.requestManager = requestManager

        videoAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                openVideo(position)
            }
        }

        recVideos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recVideos.adapter = videoAdapter
    }

    private fun openVideo(position: Int) {
        val video = videoAdapter.getItem(position)
        val openVideo = Intent(Intent.ACTION_VIEW, Uri.parse(Constant.VIDEO_URL + video.key))
        startActivity(openVideo)
    }

    private fun getVideos(movieId: Int) {
        viewModel.getVideos(movieId)

        viewModel.onGetVideosResult().observe(this,
            Observer<Video<VideoResult>> {
                videoAdapter.videos = it
                setupRecyclerView()
            })


        viewModel.onGetVideoError().observe(this, Observer<String> {
            displaySnackBar(it)
        })
    }

    private fun getFavoritedMovies(movieId: Int) {
        viewModel.getFavouritedMovie(movieId)

        viewModel.onGetFavouritedResult().observe(this,
            Observer {
                if (movie.id == it.id) {
                    fab_favorites.setImageResource(R.drawable.ic_star_24dp)
                    fab_favorites.setOnClickListener {
                        deleteFavourites(movie.id)
                        TheMovieWidget.refreshWidget(this)
                    }
                }
            })

        viewModel.onGetFavouritedError().observe(this, Observer {
            fab_favorites.setImageResource(R.drawable.ic_star_border_24dp)
            fab_favorites.setOnClickListener {
                saveFavourites(movie.id)
                TheMovieWidget.refreshWidget(this)
            }
        })
    }

    private fun saveFavourites(movieId: Int) {
        viewModel.saveFavourites(this, movieId)
        viewModel.onSuccessSaveFavorites().observe(this, Observer {
            fab_favorites.setImageResource(R.drawable.ic_star_24dp)
            fab_favorites.setOnClickListener {
                deleteFavourites(movie.id)
            }
            displaySnackBar(it)
            viewModel.disposeSaveFavourites()
        })
    }

    private fun deleteFavourites(movieId: Int) {
        viewModel.deleteFavourites(this, movieId)
        viewModel.onSuccessDeleteFavorites().observe(this, Observer {
            fab_favorites.setImageResource(R.drawable.ic_star_border_24dp)
            fab_favorites.setOnClickListener {
                saveFavourites(movie.id)
            }
            displaySnackBar(it)
            viewModel.disposeDeleteFavourites()
        })


    }

    override fun onDestroy() {
        viewModel.disposeElements()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
