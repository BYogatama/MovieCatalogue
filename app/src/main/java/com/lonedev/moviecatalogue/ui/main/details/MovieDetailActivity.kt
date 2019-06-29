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
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.ui.adapter.VideoAdapter
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


    override fun layoutResource(): Int {
        return R.layout.activity_detail_movies
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel::class.java)
        videoAdapter = VideoAdapter(this)

        val movie = intent.getParcelableExtra<MovieResult>("movie")

        requestManager.load(generatreImageUrl(movie.backdropPath, Constant.WOriginal))
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
        viewModel.loadVideos(movieId)

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
