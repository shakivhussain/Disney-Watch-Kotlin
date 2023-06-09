package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.movie.MovieDetails
import com.shakiv.husain.disneywatch.data.model.details.tvshow.TvShowDetails
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreviewResponse
import com.shakiv.husain.disneywatch.util.ApiConstants
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_API
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_TV_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query(ApiConstants.KEY_QUERY) query: String,
        @Query(ApiConstants.KEY_PAGE) page: Int,
        @Query(ApiConstants.KEY_API) apiKey: String
    ): Response<BaseResponse<List<Movie>>>

    @GET("tv/{${KEY_TV_ID}}")
    suspend fun getTvShowDetails(
        @Path(KEY_TV_ID) tvShowId: String,
        @Query(KEY_API) apiKey: String
    ): Response<TvShowDetails>



    @GET("tv/{$KEY_TV_ID}/credits")
    suspend fun getCredits(
        @Path(KEY_TV_ID) tvShowId: String,
        @Query(KEY_API) apiKey: String,
    ) : Response<CastResponse>

    @GET("tv/{$KEY_TV_ID}/videos")
    suspend fun getTvShowVideos(
        @Path(KEY_TV_ID) tvShowId: String,
        @Query(KEY_API) apiKey: String
    ): Response<MoviePreviewResponse>

    @GET("tv/{$KEY_TV_ID}/recommendations")
    suspend fun getRecommendations(
        @Path(KEY_TV_ID) tvShowId: String,
        @Query(ApiConstants.KEY_PAGE) page: Int = 1,
        @Query(KEY_API) apiKey: String
    ) :Response<BaseResponse<List<Movie>>>

}