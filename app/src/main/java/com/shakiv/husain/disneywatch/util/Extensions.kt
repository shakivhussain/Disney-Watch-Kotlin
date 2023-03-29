package com.shakiv.husain.disneywatch.util

fun String.convertToFullUrl(): String {
    return "${Constants.BASE_URL_WITH_ORIGINAL+this}"
}