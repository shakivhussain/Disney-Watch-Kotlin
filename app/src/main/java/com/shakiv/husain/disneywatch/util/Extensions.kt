package com.shakiv.husain.disneywatch.util

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.shakiv.husain.disneywatch.data.network.Resource
import timber.log.Timber

fun String.convertToFullUrl(): String {
    return "${ApiConstants.BASE_URL_WITH_ORIGINAL + this}"
}


fun Fragment.navigate(directions: Int, bundle: Bundle? = null) {
    findNavController().navigate(directions, bundle)
}


fun Fragment.getStringFromId(id:Int) : String{
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

