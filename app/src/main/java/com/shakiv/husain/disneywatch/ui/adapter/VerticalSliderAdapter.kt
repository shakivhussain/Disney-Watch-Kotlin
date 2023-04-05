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
import com.shakiv.husain.disneywatch.util.logd
import com.shakiv.husain.disneywatch.util.toStringOrEmpty

class VerticalSliderAdapter(
    private val onItemClicked: (String) -> Unit
) : PagingDataAdapter<Movie, VerticalSliderAdapter.SliderViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {

        val binding = LayoutSliderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SliderViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class SliderViewHolder(
        private val binding: LayoutSliderItemBinding, onItemClicked: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var _movie: Movie? = null

        init {
            with(binding) {
                root.setOnClickListener {
                    _movie?.id?.let {
                        onItemClicked.invoke(it.toStringOrEmpty())
                    }
                }
            }
        }

        fun bind(position: Int) {
            val movie = getItem(position)
            _movie = movie

            val imageUrl = "${BASE_URL_WITH_ORIGINAL + movie?.poster_path}"
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