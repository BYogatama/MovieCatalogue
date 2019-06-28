package com.lonedev.moviecatalogue.ui.main.details

import android.os.Bundle
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.base.BaseActivity
import com.lonedev.moviecatalogue.data.models.MovieResult
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var requestManager: RequestManager

    override fun layoutResource(): Int {
        return R.layout.activity_detail_movies
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = intent.getParcelableExtra<MovieResult>("movie")

//        if (movie.image != 0) {
//            requestManager.load(movie.image)
//                .into(movie_poster)
//        } else {
//            requestManager.load(movie.imageUrl)
//                .into(movie_poster)
//        }
//
//        movie_title.text = movie.name
//        movie_release.text = movie.releaseDate
//
//        if (Locale.US == Locale.getDefault()) {
//            movie_genres.text = movie.genres?.en
//            movie_runtime.text = movie.runtime?.en
//            movie_lang.text = movie.language?.en
//            movie_status.text = movie.status?.en
//            movie_overview.text = movie.overview?.en
//        } else {
//            movie_genres.text = movie.genres?.id
//            movie_runtime.text = movie.runtime?.id
//            movie_lang.text = movie.language?.id
//            movie_status.text = movie.status?.id
//            movie_overview.text = movie.overview?.id
//        }
//
//        movie_score.text = movie.score.toString()
//
//        supportActionBar?.title = movie.name
//
//        btn_buy_ticket.setOnClickListener {
//            Snackbar.make(it, movie.name.toString(), Snackbar.LENGTH_SHORT).show()
//        }

    }

}
