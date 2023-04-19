package com.shakiv.husain.disneywatch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.husain.disneywatch.data.api.MovieService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY

class UpcomingMoviePagingSource(val service: MovieService) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val key = params.key ?: 1
        return try {
            val data = NetworkRequest.process {
                service.getUpComingMovies(API_KEY, key)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {
                        results ?: throw Exception("Error in fetching upcoming movies.")
                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error in fetching upcoming movies.")
                    }
                }
            }
            val movies = data.data ?: emptyList()
            val nextPage = data.page?.plus(1)
            LoadResult.Page(movies, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}