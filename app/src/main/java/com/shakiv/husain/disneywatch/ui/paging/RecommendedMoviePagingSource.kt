package com.shakiv.husain.disneywatch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY

class RecommendedMoviePagingSource(
    private val service: NetworkService,
    private val movieId: String
) : PagingSource<Int, Movie>() {


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val key = params.key ?: 1

        return try {
            val data = NetworkRequest.process {
                service.getRecommendedMovies(movie_id = movieId, API_KEY, page = key)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {

                        results ?: throw Exception("Error In recommended movies.")
                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error In recommended movies.")
                    }
                }
            }
            val movies = data.data ?: emptyList()
            val nextPage = data.page?.plus(1) ?: 1
            LoadResult.Page(movies, null, nextPage)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}