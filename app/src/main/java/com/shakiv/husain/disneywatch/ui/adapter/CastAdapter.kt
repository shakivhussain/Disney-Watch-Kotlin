package com.shakiv.husain.disneywatch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.data.model.cast.Cast
import com.shakiv.husain.disneywatch.databinding.ItemCastViewBinding
import com.shakiv.husain.disneywatch.util.ImageUtils
import com.shakiv.husain.disneywatch.util.convertToFullUrl

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CastViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CastViewHolder(private val binding: ItemCastViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = getItem(position)
            val imageUrl = item.profile_path?.convertToFullUrl()?:""
            val name = item.name.orEmpty()
            val character = item.character.orEmpty()

            binding.apply {
                ImageUtils.setImage(imageUrl, binding.ivProfile)
                tvName.text= name
                tvChar.text = character
            }





        }

    }


    companion object COMPARATOR : DiffUtil.ItemCallback<Cast>() {
        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return false
        }

        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return false
        }
    }
}