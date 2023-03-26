package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.Movie
import com.shakiv.husain.disneywatch.util.Constants.KEY_API
import com.shakiv.husain.disneywatch.util.Constants.KEY_PAGE
import com.shakiv.husain.disneywatch.util.Constants.POPULAR_MOVIES
import com.shakiv.husain.disneywatch.util.Constants.TOP_RATED_MOVIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query(KEY_API) apiKey: String,
        @Query(KEY_PAGE) page: Int = 1
    ): Response<BaseResponse<List<Movie>>>

    @GET(TOP_RATED_MOVIES)
    suspend fun getTrendingMovies(
        @Query(KEY_API) apiKey: String,
        @Query(KEY_PAGE) page: Int=1
    ): Response<BaseResponse<List<Movie>>>

}