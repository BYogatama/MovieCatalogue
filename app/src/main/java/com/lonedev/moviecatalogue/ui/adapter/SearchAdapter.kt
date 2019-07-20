/*
 * Created by Bagus Yogatama on 7/18/19 5:31 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 5:31 PM
 */

package com.lonedev.moviecatalogue.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.SearchResult
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import com.lonedev.moviecatalogue.utils.Constant
import com.lonedev.moviecatalogue.utils.OnItemClickListener
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter
constructor(private val context: Context, private val requestManager: RequestManager) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    lateinit var searchResults: List<SearchResult>
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchResults[position], requestManager)
    }

    class ViewHolder(
        itemView: View,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val moviePoster = itemView.movie_poster
        private val movieTitle = itemView.movie_title
        private val movieOverview = itemView.movie_overview

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(searchResult: SearchResult, requestManager: RequestManager) {

            if (searchResult.title!!.isEmpty()) {
                movieTitle.text = searchResult.name
            } else {
                movieTitle.text = searchResult.title
            }

            val imageUrl = "${Constant.IMAGE_URL}${Constant.W300}${searchResult.posterPath}"
            requestManager.load(imageUrl).into(moviePoster)

            movieOverview.text = searchResult.overview


        }


        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }

    }
}