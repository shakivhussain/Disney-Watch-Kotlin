package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.image.Image
import com.shakiv.husain.disneywatch.databinding.LayoutHorizontalSliderItemBinding
import com.shakiv.husain.disneywatch.util.ImageUtils
import com.shakiv.husain.disneywatch.util.convertToFullUrl

class HorizontalImageAdapter(
    private val onItemClick: (Image) -> Unit
) : ListAdapter<Image, HorizontalImageAdapter.HorizontalImageVieHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalImageVieHolder {

        val binding = LayoutHorizontalSliderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return HorizontalImageVieHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: HorizontalImageVieHolder, position: Int) {
        holder.bind(position)
    }


    inner class HorizontalImageVieHolder(
        private val layoutHorizontalSliderItemBinding: LayoutHorizontalSliderItemBinding,
        onItemClick: (Image) -> Unit
    ) :
        RecyclerView.ViewHolder(layoutHorizontalSliderItemBinding.root) {

        var _image: Image? = null

        init {
            with(layoutHorizontalSliderItemBinding) {

                root.setOnClickListener {
                    _image?.let { onItemClick.invoke(it) }
                }
            }
        }

        fun bind(position: Int) {
            val image = getItem(position)
            _image = image
            layoutHorizontalSliderItemBinding.apply {
                val imageUrl = image.file_path.convertToFullUrl()
                ImageUtils.setImage(imageUrl, ivPoster)
                container.isVisible = false
            }
        }
    }

    companion object COMPARATOR : DiffUtil.ItemCallback<Image>() {
        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return false
        }

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return false
        }
    }


}