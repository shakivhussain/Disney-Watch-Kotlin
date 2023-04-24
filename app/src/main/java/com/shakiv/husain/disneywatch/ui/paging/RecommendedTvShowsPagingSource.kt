package com.shakiv.husain.disneywatch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.husain.disneywatch.data.api.TvShowService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY
import com.shakiv.husain.disneywatch.util.orThrow
import com.shakiv.husain.disneywatch.util.throwError

class RecommendedTvShowsPagingSource(
    private val tvShowId: String,
    private val tvShowService: TvShowService
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val recommendedTvShowsResponse = NetworkRequest.process {
                tvShowService.getRecommendations(tvShowId, page = page, API_KEY)
            }.run {

                when (this) {
                    is ApiResponse.Success -> {
                        results.orThrow("Recommended Tv Show")
                    }

                    is ApiResponse.Failure -> {
                        throwError("Recommended Tv Show")
                    }
                }
            }

            val recommendedTvShowsList = recommendedTvShowsResponse.data ?: emptyList()
            var nextKey = recommendedTvShowsResponse.page?.plus(1)

            if (recommendedTvShowsList.isEmpty()) {
                nextKey = null
            }
            LoadResult.Page(data = recommendedTvShowsList, prevKey = null, nextKey = nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }

    }
}