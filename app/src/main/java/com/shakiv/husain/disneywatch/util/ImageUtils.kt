package com.shakiv.husain.disneywatch.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.shakiv.husain.disneywatch.R

object ImageUtils {


    fun setProfileImage(img: String?, view: ImageView) {
        Glide.with(view).load(img).placeholder(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .skipMemoryCache(false)
            .dontAnimate()
            .circleCrop().into(view)
    }

    fun setImage(img: String?, imageView: ImageView) {
        Glide.with(imageView).load(img)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground) // Error Drawable
            .error(R.drawable.ic_launcher_foreground)
            .fitCenter()
            .skipMemoryCache(false)
            .into(imageView)
    }

    fun setImage(@DrawableRes res: Int?, imageView: ImageView) {
        Glide.with(imageView).load(res)
            .placeholder(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground) // Error Drawable
            .error(R.drawable.ic_launcher_foreground)
            .fitCenter()
            .skipMemoryCache(false)
            .into(imageView)
    }

}