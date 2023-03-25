package com.shakiv.husain.disneywatch.data.api

import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/popular")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey : String,
        @Query("page") page : Int = 1
    ): Response<BaseResponse<List<Movie>>>
}