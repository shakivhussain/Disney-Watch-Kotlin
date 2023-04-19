package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreviewResponse
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_API
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_MOVIE_ID
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_MOVIE_ID_PATH
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_PAGE
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_QUERY
import com.shakiv.husain.disneywatch.util.ApiConstants.POPULAR_MOVIES
import com.shakiv.husain.disneywatch.util.ApiConstants.TOP_RATED_MOVIES
import com.shakiv.husain.disneywatch.util.ApiConstants.UPCOMING_MOVIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

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

    @GET("movie/{$KEY_MOVIE_ID_PATH}")
    suspend fun getMovieDetails(
        @Path(KEY_MOVIE_ID) movie_id: String,
        @Query(KEY_API) apiKey: String
    ): Response<MovieDetails>


    @GET("movie/{$KEY_MOVIE_ID_PATH}/images")
    suspend fun getMovieImages(
        @Path(KEY_MOVIE_ID) movie_id: String,
        @Query(KEY_API) apiKey: String
    ): Response<ImageResponse>

    @GET("movie/{$KEY_MOVIE_ID_PATH}/credits")
    suspend fun getCasts(
        @Path(KEY_MOVIE_ID) movie_id: String,
        @Query(KEY_API) apiKey: String
    ): Response<CastResponse>

    @GET("movie/{$KEY_MOVIE_ID_PATH}/recommendations")
    suspend fun getRecommendedMovies(
        @Path(KEY_MOVIE_ID) movie_id: String,
        @Query(KEY_API) apiKey: String,
        @Query(KEY_PAGE) page: Int? = 1
    ): Response<BaseResponse<List<Movie>>>

    @GET("movie/{$KEY_MOVIE_ID_PATH}/videos")
    suspend fun getMovieVideos(
        @Path(KEY_MOVIE_ID) movie_id: String,
        @Query(KEY_API) apiKey: String,
        @Query(KEY_PAGE) page: Int? = 1
    ): Response<MoviePreviewResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query(KEY_QUERY) query: String,
        @Query(KEY_PAGE) page: Int,
        @Query(KEY_API) apiKey: String
    ): Response<BaseResponse<List<Movie>>>


}