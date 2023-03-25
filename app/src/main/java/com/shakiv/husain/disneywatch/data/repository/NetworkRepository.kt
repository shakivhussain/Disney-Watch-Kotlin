package com.shakiv.husain.disneywatch.data.repository

import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.util.Constants.API_KEY
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun getTopRatedMovies() = flow {
        emit(Resource.Loading())
        try {
            val data = NetworkRequest.process {
                networkService.getTopRatedMovies(API_KEY, 1)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results?.data ?: throw Exception("Error Fetching Top Movies.")

                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error Fetching Top Movies.")
                    }
                }
            }

            emit(Resource.Success(data = data))
        } catch (e: Exception) {
            emit(Resource.Failure())
            e.printStackTrace()
        }
    }
}