package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.Movie
import com.shakiv.husain.disneywatch.databinding.LayoutVerticalMovieItemBinding

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.VerticalMovieViewHolder>(Comparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMovieViewHolder {
        val binding = LayoutVerticalMovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VerticalMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalMovieViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class VerticalMovieViewHolder(val binding: LayoutVerticalMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)
            binding.apply {
                tvTitle.text= item?.title
                tvSubTitle.text= item?.overview
            }
        }

    }


    companion object Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return false
        }
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return false
        }
    }
}