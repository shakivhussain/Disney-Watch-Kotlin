package com.shakiv.husain.disneywatch.ui.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.data.repository.TvShowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowViewModel @Inject constructor(private val tvShowRepository: TvShowRepository) :
    ViewModel() {
    private val _tvShowsPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val tvShowsPagingData = _tvShowsPagingData.asStateFlow()

    private val _tvShowDetails = MutableStateFlow<Resource<MovieDetails>?>(null)
    val tvShowDetails = _tvShowDetails.asStateFlow()

    fun searchTvShow(query: String) {
        viewModelScope.launch {
            tvShowRepository
                .searchTvShows(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _tvShowsPagingData.emit(it)
                }
        }
    }

    fun getTvShowDetails(tvShowId: String) {
        viewModelScope.launch {
            tvShowRepository
                .getTvShowDetails(tvShowId = tvShowId)
                .collectLatest {
                    _tvShowDetails.emit(it)
                }
        }
    }

}