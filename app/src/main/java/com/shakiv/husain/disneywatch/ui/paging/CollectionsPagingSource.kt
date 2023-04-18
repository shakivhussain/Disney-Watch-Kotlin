package com.shakiv.husain.disneywatch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakiv.husain.disneywatch.data.api.CollectionService
import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.ApiResponse
import com.shakiv.husain.disneywatch.data.network.NetworkRequest
import com.shakiv.husain.disneywatch.util.ApiConstants.API_KEY
import com.shakiv.husain.disneywatch.util.orThrow
import com.shakiv.husain.disneywatch.util.throwError

class CollectionsPagingSource(
    private val query: String,
    private val service: CollectionService
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return runCatching {
            val page = params.key ?: 1
            val data = NetworkRequest.process {
                service.searchCollections(query = query, page = page, apiKey = API_KEY)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        results.orThrow("collection")
                    }
                    is ApiResponse.Failure -> {
                        throwError("collection")
                    }
                }
            }

            val collections = data.data ?: emptyList()
            var nextPage = data.page
            nextPage = nextPage?.plus(1)
            if (collections.isNullOrEmpty()) {
                nextPage = null
            }
            LoadResult.Page(collections, null, nextPage)
        }.getOrElse {
            it.printStackTrace()
            LoadResult.Error(it)
        }
    }

}