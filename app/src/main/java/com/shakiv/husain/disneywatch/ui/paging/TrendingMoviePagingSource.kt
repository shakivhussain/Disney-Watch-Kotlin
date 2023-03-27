package com.shakiv.husain.disneywatch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.util.Constants.API_KEY

class TrendingMoviePagingSource(private val networkService: NetworkService) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: 1

        return try {
            val data = NetworkRequest.process {
                networkService.getTrendingMovies(API_KEY, page)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {
                        results ?: throw Exception("Error in fething trending movies.")
                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error in fething trending movies.")
                    }
                }
            }
            val trendingMovies = data.data ?: emptyList()
            val nextPage = data.page?.plus(1) ?: 1
            LoadResult.Page(trendingMovies, null, nextPage)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }

    }
}