package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.details.movie.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.util.ApiConstants
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_API
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_COLLECTION_ID
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_PAGE
import com.shakiv.husain.disneywatch.util.ApiConstants.KEY_QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionService {

    @GET("search/collection")
    suspend fun searchCollections(
        @Query(KEY_QUERY) query: String,
        @Query(KEY_PAGE) page: Int,
        @Query(KEY_API) apiKey: String
    ): Response<BaseResponse<List<Movie>>>

    @GET("collection/{${ApiConstants.KEY_COLLECTION_ID}}/images")
    suspend fun getCollectionsImages(
        @Path(KEY_COLLECTION_ID) collectionId: String,
        @Query(KEY_API) apiKey: String
    ): Response<ImageResponse>


    @GET("collection/{$KEY_COLLECTION_ID}")
    suspend fun getCollectionDetails(
        @Path(KEY_COLLECTION_ID) collectionId: String,
        @Query(KEY_API) apiKey: String
    ): Response<MovieDetails>




}