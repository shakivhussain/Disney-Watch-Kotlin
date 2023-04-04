package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreview
import com.shakiv.husain.disneywatch.databinding.ItemHorizontalMovieVideoBinding

class HorizontalVideoAdapter :
    ListAdapter<MoviePreview, HorizontalVideoAdapter.HorizontalViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val binding = ItemHorizontalMovieVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HorizontalViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class HorizontalViewHolder(binding: ItemHorizontalMovieVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
        }


        fun bind(position: Int) {
            val moviePreview = getItem(position)
        }
    }

    companion object COMPARATOR : DiffUtil.ItemCallback<MoviePreview>() {
        override fun areContentsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean {
            return false
        }

        override fun areItemsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean {
            return false
        }
    }
}