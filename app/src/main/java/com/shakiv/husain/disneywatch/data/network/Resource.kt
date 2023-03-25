package com.shakiv.husain.disneywatch.data.network

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val error: Int? = null
) {

    class Success<T>(
        data: T? = null, message: String? = null, code: Int? = null
    ) : Resource<T>(data, message, code)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class Failure<T>(
        data: T? = null, message: String? = null, code: Int? = null
    ) : Resource<T>(data, message, code)
}