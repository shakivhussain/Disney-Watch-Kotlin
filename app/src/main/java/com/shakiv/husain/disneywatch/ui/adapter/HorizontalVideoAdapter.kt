package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreview
import com.shakiv.husain.disneywatch.databinding.ItemHorizontalMovieVideoBinding

class HorizontalVideoAdapter(
    val lifecycle: Lifecycle
) :
    ListAdapter<MoviePreview, HorizontalVideoAdapter.HorizontalViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val binding = ItemHorizontalMovieVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HorizontalViewHolder(binding, lifecycle)
    }


    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class HorizontalViewHolder(
        binding: ItemHorizontalMovieVideoBinding,
        private val lifecycle: Lifecycle
    ) : RecyclerView.ViewHolder(binding.root) {
        private var _currentPreview: MoviePreview? = null
        private var youTubePlayer: YouTubePlayer? = null


        init {
            val youTubePlayerView = binding.layoutPlayer.youtubePlayerView
            lifecycle.addObserver(youTubePlayerView)

            // the overlay view is used to intercept clicks when scrolling the recycler view
            // without it touch events used to scroll might accidentally trigger clicks in the player
            val overlayView = binding.layoutPlayer.overlayView
//            // when the overlay is clicked it starts playing the video
            overlayView.setOnClickListener { youTubePlayer?.play() }

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // store youtube player reference for later
                    this@HorizontalViewHolder.youTubePlayer = youTubePlayer
                    // cue the video if it's available
                    _currentPreview?.key?.let { youTubePlayer.cueVideo(it, 0f) }
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState
                ) {
                    when (state) {
                        // when the video is CUED, show the overlay.
                        PlayerConstants.PlayerState.VIDEO_CUED -> overlayView.isVisible = true
                        // remove the overlay for every other state, so that we don't intercept clicks and the
                        // user can interact with the player.
                        else -> overlayView.isVisible = false
                    }
                }
            })
        }


        fun bind(position: Int) {
            val moviePreview = getItem(position)
            _currentPreview = moviePreview
            val movieId = moviePreview.key
            // cue the video if the youtube player is available
            youTubePlayer?.cueVideo(movieId, 0f)
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