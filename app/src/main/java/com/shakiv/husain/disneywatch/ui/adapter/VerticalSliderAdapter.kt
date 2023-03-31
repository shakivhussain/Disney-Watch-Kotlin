package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.databinding.LayoutSliderItemBinding
import com.shakiv.husain.disneywatch.util.ApiConstants.BASE_URL_WITH_ORIGINAL
import com.shakiv.husain.disneywatch.util.ImageUtils

class VerticalSliderAdapter : PagingDataAdapter<Movie, VerticalSliderAdapter.SliderViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {

        val binding = LayoutSliderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class SliderViewHolder(private val binding: LayoutSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = getItem(position)
            val imageUrl = "${BASE_URL_WITH_ORIGINAL+item?.poster_path}"
            ImageUtils.setImage(imageUrl, binding.ivPoster)
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