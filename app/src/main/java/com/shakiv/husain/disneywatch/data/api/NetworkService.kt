package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_API
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_PAGE
import com.shakiv.husain.disneywatch.util.ApiConstants.POPULAR_MOVIES
import com.shakiv.husain.disneywatch.util.ApiConstants.TOP_RATED_MOVIES
import com.shakiv.husain.disneywatch.util.ApiConstants.UPCOMING_MOVIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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
        @Query(KEY_PAGE) page: Int = 1
    ): Response<BaseResponse<List<Movie>>>


    @GET(UPCOMING_MOVIES)
    suspend fun getUpComingMovies(
        @Query(KEY_API) apiKey: String,
        @Query(KEY_PAGE) page: Int = 1
    ): Response<BaseResponse<List<Movie>>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: String,
        @Query(KEY_API) apiKey: String
    ): Response<MovieDetails>


    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movie_id: String,
        @Query(KEY_API) apiKey: String
    ): Response<ImageResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(
        @Path("movie_id") movie_id : String,
        @Query(KEY_API) apiKey: String
    ) : Response<CastResponse>

}