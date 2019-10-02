/*
 * Created by Bagus Yogatama on 9/22/19 9:34 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 9:20 PM
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
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.utils.Constant

class TVListAdapter constructor(private val context: Context) :
    PagedListAdapter<TVSeriesResult, TVListAdapter.TVViewHolder>(COMPARATOR) {

    lateinit var onItemClickListener: (TVSeriesResult) -> Unit
    lateinit var requestManager: RequestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return TVViewHolder(view, onItemClickListener, requestManager)
    }

    override fun onBindViewHolder(holder: TVViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<TVSeriesResult>() {
            override fun areContentsTheSame(oldItem: TVSeriesResult, newItem: TVSeriesResult): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: TVSeriesResult, newItem: TVSeriesResult): Boolean =
                oldItem.name == newItem.name
        }
    }

    class TVViewHolder(
        itemView: View,
        private val onItemClickListener: (TVSeriesResult) -> Unit,
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

        fun bind(tvSeries: TVSeriesResult) {

            var imageUrl = "${Constant.IMAGE_URL}${Constant.W300}"

            imageUrl += tvSeries.posterPath
            movieTitle.text = tvSeries.name

            requestManager.load(imageUrl)
                .into(moviePoster)

            itemView.setOnClickListener { onItemClickListener(tvSeries) }

        }
    }
}