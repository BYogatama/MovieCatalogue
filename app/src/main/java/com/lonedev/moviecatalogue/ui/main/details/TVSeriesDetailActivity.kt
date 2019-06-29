/*
 * Created by Bagus Yogatama on 6/28/19 10:41 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 10:41 PM
 */

package com.lonedev.moviecatalogue.ui.main.details

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.ui.adapter.VideoAdapter
import com.lonedev.moviecatalogue.utils.Constant
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_movies.*
import javax.inject.Inject

class TVSeriesDetailActivity : BaseActivity() {
    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: TVSeriesDetailViewModel
    private lateinit var videoAdapter: VideoAdapter

    @BindView(R.id.movie_trailer)
    lateinit var recVideos: RecyclerView


    override fun layoutResource(): Int {
        return R.layout.activity_detail_movies
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        viewModel = ViewModelProviders.of(this, factory).get(TVSeriesDetailViewModel::class.java)
        videoAdapter = VideoAdapter(this)

        val tvSeries = intent.getParcelableExtra<TVSeriesResult>("tv")

        requestManager.load(generatreImageUrl(tvSeries.backdropPath, Constant.WOriginal))
            .into(movie_poster)

        movie_title.text = tvSeries.name
        displayScore(tvSeries)

        movie_release.text = formatDate(tvSeries.firstAirDate)

        movie_overview.text = tvSeries.overview

        getVideos(tvSeriesId = tvSeries.id)


    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun displayScore(tvSeries: TVSeriesResult) {
        val score: Int = tvSeries.voteAverage?.times(10)!!.toInt()
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

    private fun getVideos(tvSeriesId: Int) {
        viewModel.loadVideos(tvSeriesId)

        viewModel.videoResult().observe(this,
            Observer<Video<VideoResult>> {
                videoAdapter.videos = it
                setupRecyclerView()
            })

        viewModel.videoError().observe(this, Observer<String> {
            //            displaySnackBar(it)
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