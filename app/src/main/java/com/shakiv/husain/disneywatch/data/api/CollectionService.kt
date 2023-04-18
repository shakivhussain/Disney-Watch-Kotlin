package com.shakiv.husain.disneywatch.data.api

import com.shakiv.husain.disneywatch.data.model.BaseResponse
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.util.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionService {
    
    @GET("search/collection")
    suspend fun searchCollections(
        @Query(ApiConstants.KEY_QUERY) query: String,
        @Query(ApiConstants.KEY_PAGE) page: Int,
        @Query(ApiConstants.KEY_API) apiKey: String
    ): Response<BaseResponse<List<Movie>>>

    @GET("collection/{${ApiConstants.KEY_COLLECTION_ID}}/images")
    suspend fun getCollectionsImages(
        @Path(ApiConstants.KEY_COLLECTION_ID) collectionId: String,
        @Query(ApiConstants.KEY_API) apiKey: String
    ): Response<ImageResponse>

}