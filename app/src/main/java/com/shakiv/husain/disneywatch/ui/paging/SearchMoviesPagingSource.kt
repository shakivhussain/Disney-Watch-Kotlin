package com.shakiv.husain.disneywatch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.husain.disneywatch.data.api.MovieService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY

class SearchMoviesPagingSource(
    private val service: MovieService,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val data = NetworkRequest.process {
                service.searchMovies(query = query, page = page, apiKey = API_KEY)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {
                        results ?: throw Exception("Error in search Movies.")
                    }
                    is ApiResponse.Failure -> {
                        throw Exception("Error in search Movies.")
                    }
                }
            }

            val moviesList = data.data ?: emptyList()
            var nextPage = data.page?.plus(1)

            if (moviesList.isEmpty()) {
                nextPage = null
            }
            LoadResult.Page(moviesList, null, nextKey = nextPage)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }


    }

}