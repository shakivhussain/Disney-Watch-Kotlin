package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.util.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query(ApiConstants.KEY_QUERY) query: String,
        @Query(ApiConstants.KEY_PAGE) page: Int,
        @Query(ApiConstants.KEY_API) apiKey: String
    ): Response<BaseResponse<List<Movie>>>

}