package com.shakiv.husain.disneywatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.api.MovieService
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.movie.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreviewResponse
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.ui.paging.*
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY
import com.shakiv.husain.disneywatch.util.orThrow
import com.shakiv.husain.disneywatch.util.throwError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val networkService: MovieService
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

    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)
        return Pager(config) {
            UpcomingMoviePagingSource(networkService)
        }.flow
    }

    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)

        return Pager(config) {
            TrendingMoviePagingSource(networkService = networkService)
        }.flow
    }


    fun getRecommendedMovies(movieId: String): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, false, 20)
        return Pager(config = config) {
            RecommendedMoviePagingSource(service = networkService, movieId = movieId)
        }.flow
    }

    fun getMovieDetails(movieId: String) = flow<Resource<MovieDetails>> {

        emit(Resource.Loading())
        try {
            val data = NetworkRequest.process {
                networkService.getMovieDetails(apiKey = API_KEY, movie_id = movieId)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results ?: throw Exception("Error in movie details. ")
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



    fun getMovieImages(movieId: String) = flow<Resource<ImageResponse>> {
        emit(Resource.Loading())
        try {
            val data = NetworkRequest.process {
                networkService.getMovieImages(movieId, apiKey = API_KEY)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results ?: throw java.lang.Exception("Error in movies images.")
                    }
                    is ApiResponse.Failure -> {
                        throw java.lang.Exception("Error in movies images.")
                    }
                }
            }
            emit(Resource.Success(data = data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(data = null, message = e.localizedMessage))
        }
    }


    fun getCasts(movieId: String) = flow<Resource<CastResponse>> {
        emit(Resource.Loading())
        try {
            val data = NetworkRequest.process {
                networkService.getCasts(movieId, API_KEY)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results ?: throw Exception("Error in fetching casts.")
                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error in fetching casts.")
                    }
                }
            }
            emit(Resource.Success(data = data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(data = null, message = e.localizedMessage))
        }
    }


    fun getMovieVideos(movieId: String) = flow<Resource<MoviePreviewResponse>> {
        emit(Resource.Loading())
        try {
            val previewResponse = NetworkRequest.process {
                networkService.getMovieVideos(movie_id = movieId, API_KEY)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results.orThrow("Error in movie preview...")
                    }
                    is ApiResponse.Failure -> {
                        throwError("Error in movie preview.")
                    }
                }
            }
            emit(Resource.Success(data = previewResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(data = null, e.localizedMessage))
        }
    }


    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)
        return Pager(config) {
            SearchMoviesPagingSource(query = query, service = networkService)
        }.flow
    }

}