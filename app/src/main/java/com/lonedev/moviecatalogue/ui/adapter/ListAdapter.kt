/*
 * Created by Bagus Yogatama on 6/22/19 6:31 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/22/19 2:41 PM
 */

package com.lonedev.moviecatalogue.ui.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.utils.Constant
import com.lonedev.moviecatalogue.utils.OnItemClickListener

class ListAdapter<T : Parcelable>
constructor(private val context: Context) :
    RecyclerView.Adapter<ListAdapter.MovieViewHolder<T>>() {

    lateinit var movies: Movie<T>
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var requestManager: RequestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder<T> {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onItemClickListener, requestManager)
    }

    override fun getItemCount(): Int {
        return movies.results.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder<T>, position: Int) {
        val movie = movies.results[position]
        holder.bind(movie)
    }

    class MovieViewHolder<T : Parcelable>(
        itemView: View,
        private val onItemClickListener: OnItemClickListener,
        private val requestManager: RequestManager
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.movie_poster)
        lateinit var moviePoster: ImageView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun bind(movie: T) {

            var imageUrl = Constant.IMAGE_URL + Constant.W300

            imageUrl += if (movie::class.java == MovieResult::class.java) {
                val m = movie as MovieResult
                m.posterPath
            } else {
                val m = movie as TVSeriesResult
                m.posterPath
            }

            requestManager.load(imageUrl)
                .into(moviePoster)

        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

}