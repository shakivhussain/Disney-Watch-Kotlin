package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.databinding.LayoutHorizontalSliderItemBinding
import com.shakiv.husain.disneywatch.util.ApiConstants.BASE_URL_WITH_ORIGINAL
import com.shakiv.husain.disneywatch.util.ImageUtils
import com.shakiv.husain.disneywatch.util.logd
import com.shakiv.husain.disneywatch.util.toStringOrEmpty

class HorizontalSliderAdapter(private val onItemClick: (String) -> Unit) :
    PagingDataAdapter<Movie, HorizontalSliderAdapter.SliderViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {

        val binding = LayoutHorizontalSliderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SliderViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class SliderViewHolder(
        private val binding: LayoutHorizontalSliderItemBinding, onItemClick: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var _movie: Movie? = null

        init {
            with(binding) {
                root.setOnClickListener {


                    _movie?.id?.let {id->
                        onItemClick.invoke(id.toStringOrEmpty())
                    }
                }
            }
        }

        fun bind(position: Int) {
            val movie = getItem(position)
            _movie = movie

            val imageUrl = "${BASE_URL_WITH_ORIGINAL + movie?.backdrop_path}"
            ImageUtils.setImage(imageUrl, binding.ivPoster)

            binding.tvMovieName.text = movie?.title ?: ""
            binding.tvDate.text = movie?.release_date ?: ""

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