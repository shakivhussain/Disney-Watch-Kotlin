package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.PlaceHolder
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("products")
    suspend fun getProducts(): Response<List<PlaceHolder>>
}