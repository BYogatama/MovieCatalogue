/*
 * Created by Bagus Yogatama on 6/29/19 8:32 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 8:32 AM
 */

package com.lonedev.moviecatalogue.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.utils.Constant
import com.lonedev.moviecatalogue.utils.OnItemClickListener

class VideoAdapter
constructor(private val context: Context) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {


    lateinit var videos: Video<VideoResult>
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var requestManager: RequestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view, onItemClickListener, requestManager)
    }

    override fun getItemCount(): Int {
        return videos.results.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos.results[position]
        holder.bind(video)
    }

    fun getItem(position: Int) : VideoResult{
        return videos.results[position]
    }

    class VideoViewHolder(
        itemView: View,
        private val onItemClickListener: OnItemClickListener,
        private val requestManager: RequestManager
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.video_thumbnail)
        lateinit var thumbnail: ImageView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun bind(video: VideoResult) {

            val videoUrl = Constant.THUMBNAIL + video.key +"/0.jpg"

            requestManager.load(videoUrl)
                .into(thumbnail)

        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }

    }
}