package com.shakiv.husain.disneywatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.husain.disneywatch.data.api.TvShowService
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.ui.paging.TvShowPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowRepository @Inject constructor(private val tvShowService: TvShowService) {

    fun searchTvShows(query: String): Flow<PagingData<Movie>> {
        val config = PagingConfig(20, 4, true, 20)
        return Pager(config) {
            TvShowPagingSource(query = query, service = tvShowService)
        }.flow
    }

}