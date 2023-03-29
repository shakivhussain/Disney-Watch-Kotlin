package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.databinding.LayoutVerticalMovieItemBinding
import com.shakiv.husain.disneywatch.util.ImageUtils
import com.shakiv.husain.disneywatch.util.convertToFullUrl

class MovieAdapter(
    val onItemClicked: (Movie) -> Unit

) : PagingDataAdapter<Movie, MovieAdapter.VerticalMovieViewHolder>(Comparator) {

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
                item?.poster_path
                val imgUrl = item?.poster_path?.convertToFullUrl()
                ImageUtils.setImage(imgUrl, binding.ivPoster)
                root.setOnClickListener {
                    onItemClicked.invoke(item ?: return@setOnClickListener)
                }
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