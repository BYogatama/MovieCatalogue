/*
 * Created by Bagus Yogatama on 7/20/19 8:17 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 8:11 PM
 */

package com.lonedev.favouriteapp.ui.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.lonedev.favouriteapp.R
import com.lonedev.favouriteapp.data.models.MovieResult
import com.lonedev.favouriteapp.data.models.TVSeriesResult
import com.lonedev.favouriteapp.utils.Constant
import com.lonedev.favouriteapp.utils.OnItemClickListener

class ListAdapter<T : Parcelable>
constructor(private val context: Context) : RecyclerView.Adapter<ListAdapter.MovieViewHolder<T>>() {

    lateinit var movies: List<T>
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var requestManager: RequestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder<T> {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(
            view,
            onItemClickListener,
            requestManager
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder<T>, position: Int) {
        val movie = movies[position]
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
        @BindView(R.id.movie_title)
        lateinit var movieTitle: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun bind(movie: T) {

            var imageUrl = "${Constant.IMAGE_URL}${Constant.W300}"

            if (movie::class.java == MovieResult::class.java) {
                val m = movie as MovieResult
                imageUrl += m.posterPath
                movieTitle.text = movie.title

            } else {
                val m = movie as TVSeriesResult
                imageUrl += m.posterPath
                movieTitle.text = movie.name
            }

            requestManager.load(imageUrl)
                .into(moviePoster)

        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

}