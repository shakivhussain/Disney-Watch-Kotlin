package com.shakiv.husain.disneywatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.api.CollectionService
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.ui.paging.CollectionsPagingSource
import com.shakiv.husain.disneywatch.util.ApiConstants
import com.shakiv.husain.disneywatch.util.orThrow
import com.shakiv.husain.disneywatch.util.throwError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CollectionRepository @Inject constructor(private val collectionService: CollectionService) {

    fun getCollectionsImages(collectionId: String) = flow<Resource<ImageResponse>> {
        emit(Resource.Loading())
        val message = "Error in Collection Images."
        try {
            val imageResponse = NetworkRequest.process {
                collectionService.getCollectionsImages(collectionId, ApiConstants.API_KEY)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {
                        results.orThrow(message)
                    }
                    is ApiResponse.Failure -> {
                        throwError(message = message)
                    }
                }
            }

            emit(Resource.Success(imageResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(null, e.message))
        }
    }


    fun searchCollection(query: String): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)
        return Pager(config) {
            CollectionsPagingSource(query = query, service = collectionService)
        }.flow
    }
}