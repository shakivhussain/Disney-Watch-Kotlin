package com.shakiv.husain.disneywatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.ui.paging.PopularMoviePagingSource
import com.shakiv.husain.disneywatch.ui.paging.TrendingMoviePagingSource
import com.shakiv.husain.disneywatch.ui.paging.UpcomingMoviePagingSource
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {


    suspend fun topRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        this.emit(Resource.Loading(data = null))
        try {
            val data = NetworkRequest.process {
                networkService.getPopularMovies(API_KEY, 1)
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

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)

        return Pager(config) {
            PopularMoviePagingSource(networkService)
        }.flow
    }

    fun getUpcomingMovies():Flow<PagingData<Movie>>{
        val config = PagingConfig(20,4,true,20)
        return Pager(config){
            UpcomingMoviePagingSource(networkService)
        }.flow
    }

    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)

        return Pager(config) {
            TrendingMoviePagingSource(networkService = networkService)
        }.flow
    }

    fun getMovieDetails(movieId: Int) = flow<Resource<MovieDetails>> {

        emit(Resource.Loading())
        try {
            val data=NetworkRequest.process {
                networkService.getMovieDetails(apiKey = API_KEY, movie_id =  movieId)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results?: throw Exception("Error in movie details. ")
                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error in movie details.")
                    }
                }
            }

            emit(Resource.Success(data = data))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(data = null, message = e.localizedMessage))
        }


    }


}