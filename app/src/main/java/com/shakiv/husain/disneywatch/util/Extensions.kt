package com.shakiv.husain.disneywatch.util

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


fun Fragment.getString(id:Int) : String{
    return resources.getString(id)
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

