package com.shakiv.husain.disneywatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.api.TvShowService
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.data.network.Resource
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

    fun getTvShowDetails(tvShowId:String) = flow<Resource<MovieDetails>>{
        emit(Resource.Loading())
        try {
            val tvShowResponse = NetworkRequest.process {
                tvShowService.getTvShowDetails(tvShowId, API_KEY)
            }.run {

                when(this){
                    is ApiResponse.Success->{
                        results.orThrow("Tv Show")

                    }
                    is ApiResponse.Failure->{
                        throwError("Tv Show")
                    }
                }
            }

            emit(Resource.Success(tvShowResponse))

        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(null,e.localizedMessage))
        }
    }

}