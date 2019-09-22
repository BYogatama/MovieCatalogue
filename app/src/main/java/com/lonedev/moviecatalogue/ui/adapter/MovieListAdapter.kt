/*
 * Created by Bagus Yogatama on 9/22/19 8:26 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 8:26 PM
 */

package com.lonedev.moviecatalogue.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.utils.Constant

class MovieListAdapter constructor(private val context: Context) :
    PagedListAdapter<MovieResult, MovieListAdapter.MovieViewHolder>(COMPARATOR) {

    lateinit var onItemClickListener: (MovieResult) -> Unit
    lateinit var requestManager: RequestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onItemClickListener, requestManager)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<MovieResult>() {
            override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
                oldItem.title == newItem.title
        }
    }

    class MovieViewHolder(
        itemView: View,
        private val onItemClickListener: (MovieResult) -> Unit,
        private val requestManager: RequestManager
    ) :
        RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.movie_poster)
        lateinit var moviePoster: ImageView
        @BindView(R.id.movie_title)
        lateinit var movieTitle: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(movie: MovieResult) {

            var imageUrl = "${Constant.IMAGE_URL}${Constant.W300}"

            imageUrl += movie.posterPath
            movieTitle.text = movie.title

            requestManager.load(imageUrl)
                .into(moviePoster)

            itemView.setOnClickListener { onItemClickListener(movie) }

        }
    }
}