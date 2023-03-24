package com.shakiv.husain.disneywatch.data.repository

sealed class ApiResponse<out T> {
    data class Success<out T>(
        val results: T?
    ) : ApiResponse<T>()

    data class Failure<out T>(
        val status_code: Int, val status_message: String, val results: T?, val throwable: Throwable? = null
    ) : ApiResponse<T>()

}
