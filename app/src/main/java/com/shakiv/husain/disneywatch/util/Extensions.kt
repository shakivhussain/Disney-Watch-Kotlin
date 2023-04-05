package com.shakiv.husain.disneywatch.util

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.util.AppConstants.ID
import com.shakiv.husain.disneywatch.util.AppConstants.RECYCLER_VIEW_ZERO_POSITION
import com.shakiv.husain.disneywatch.util.AppConstants.TWO_SECONDS_IN_MILLIS
import timber.log.Timber

fun String.convertToFullUrl(): String {
    return "${ApiConstants.BASE_URL_WITH_ORIGINAL + this}"
}


fun Fragment.navigate(directions: Int, bundle: Bundle? = null) {
    findNavController().navigate(directions, bundle)
}


fun Fragment.getStringFromId(id: Int): String {
    return resources.getString(id)
}

inline fun <reified T> T?.orThrow(message: String = "Object is null"): T {
    return this ?: throw Exception(message)
}

fun throwError(message: String): Nothing {
    throw Exception(message)
}

fun Long.toKNotation(): String {
    val suffix = arrayOf("", "k", "M", "B", "T", "P", "E")
    val thousand = 1000L
    var n = this
    var power = 0
    while (n >= thousand) {
        n /= thousand
        power++
    }
    return "$n${suffix[power]}"
}

fun Int.toKNotation(): String {
    val suffix = arrayOf("", "k", "M", "B", "T", "P", "E")
    val thousand = 1000
    var n = this
    var power = 0
    while (n >= thousand) {
        n /= thousand
        power++
    }
    return "$n${suffix[power]}"
}

inline fun <reified T> T?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}

fun RecyclerView.setLinearLayout(context: Context, orientation: Int) {
    layoutManager = CustomLinearLayoutManager(context, orientation, reverseLayout = false)
}

fun Fragment.navigate(actionId: Int, bundle: Bundle?, navOption: NavOptions?) {
    findNavController().navigate(actionId, bundle, navOption)
}

fun Fragment.navigateViewDetails(movieId: String, actionId: Int) {
    val bundle = Bundle().apply {
        putString(ID, movieId)
    }
    val navOption = NavOptions.Builder()
        .setEnterAnim(R.anim.fade_in)
        .setExitAnim(R.anim.fade_out)
        .build()
    navigate(actionId, bundle, navOption)
}


fun RecyclerView.getCurrentVisiblePosition(): Int {
    val layoutManager = this.layoutManager as? LinearLayoutManager
    return layoutManager?.findFirstVisibleItemPosition() ?: RECYCLER_VIEW_ZERO_POSITION
}


fun <T : RecyclerView.Adapter<RecyclerView.ViewHolder>> ViewPager2.autoScroll(
    adapter: T,
    handler: Handler = Handler(Looper.getMainLooper()),
    delay: Long = TWO_SECONDS_IN_MILLIS
) {
    var currentPosition = 0
    val runnable = Runnable {
        currentPosition = (currentPosition + 1) % adapter.itemCount
        currentItem = currentPosition
    }
    val pagerCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, delay)
        }
    }
    registerOnPageChangeCallback(pagerCallBack)
    handler.postDelayed(runnable, delay)

}


/**
 * Returns a color value based on the specified attribute ID, using the current theme.
 * If the attribute is not defined or cannot be resolved, returns the default color value.
 *
 * @param attrId The ID of the desired attribute.
 * @return The color value for the attribute.
 */
fun Context.getColorFromAttr(attrId: Int): Int {
    // Create a TypedArray object from the current theme using the attribute ID
    val typedArray = obtainStyledAttributes(intArrayOf(attrId))

    try {
        // Get the color value from the TypedArray at index 0
        return typedArray.getColor(0, Color.BLACK)
    } finally {
        // Recycle the TypedArray object to avoid memory leaks
        typedArray.recycle()
    }
}

private fun Fragment.hideActionBar() {
    (this as AppCompatActivity).supportActionBar?.hide()
}

fun logd(log: String, tag: String = "TAG") {
    Timber.tag(tag).d(log)
}

fun loge(log: String, tag: String = "TAG") {
    Timber.tag(tag).e(log)
}

