package com.shakiv.husain.disneywatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.api.TvShowService
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.tvshow.TvShowDetails
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreviewResponse
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.ui.paging.RecommendedTvShowsPagingSource
import com.shakiv.husain.disneywatch.ui.paging.TvShowPagingSource
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY
import com.shakiv.husain.disneywatch.util.orThrow
import com.shakiv.husain.disneywatch.util.throwError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowRepository @Inject constructor(private val tvShowService: TvShowService) {

    fun searchTvShows(query: String): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)
        return Pager(config) {
            TvShowPagingSource(query = query, service = tvShowService)
        }.flow
    }

    fun getTvShowDetails(tvShowId: String) = flow<Resource<TvShowDetails>> {
        emit(Resource.Loading())
        try {
            val tvShowResponse = NetworkRequest.process {
                tvShowService.getTvShowDetails(tvShowId, API_KEY)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {
                        results.orThrow("Tv Show")

                    }

                    is ApiResponse.Failure -> {
                        throwError("Tv Show")
                    }
                }
            }

            emit(Resource.Success(tvShowResponse))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(null, e.localizedMessage))
        }
    }

    fun getCredits(tvShowId: String) = flow<Resource<CastResponse>> {
        emit(Resource.Loading())
        try {
            val creditResponse = NetworkRequest.process {
                tvShowService.getCredits(tvShowId, API_KEY)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results.orThrow("Tv Show Credits")
                    }
                    is ApiResponse.Failure -> {
                        throwError("Tv Show Credits")
                    }
                }

            }
            emit(Resource.Success(creditResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(null, e.localizedMessage))
        }

    }


    fun getTvShowVideos(tvShowId: String) = flow<Resource<MoviePreviewResponse>> {
        emit(Resource.Loading())

        try {
            val videosResponse = NetworkRequest.process {

                tvShowService.getTvShowVideos(tvShowId, API_KEY)
            }.run {
                when(this){
                    is ApiResponse.Success->{
                        results.orThrow("Tv Show Videos")
                    }
                    is ApiResponse.Failure->{
                        throwError("Tv Show Videos")
                    }
                }
            }
            emit(Resource.Success(videosResponse))
        }catch (e:Exception){
            e.printStackTrace()
            emit(Resource.Failure(null,e.localizedMessage))
        }
    }


    fun getRecommendedTvShows(tvShowId: String) : Flow<PagingData<Movie>>{
        val config = PagingConfig(20,4,true,20)
        return Pager(config){
            RecommendedTvShowsPagingSource(tvShowId, tvShowService)
        }.flow
    }

}