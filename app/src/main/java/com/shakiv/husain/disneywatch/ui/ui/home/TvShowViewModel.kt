package com.shakiv.husain.disneywatch.ui.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.movie.MovieDetails
import com.shakiv.husain.disneywatch.data.model.details.tvshow.TvShowDetails
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreviewResponse
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.data.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowViewModel @Inject constructor(private val tvShowRepository: TvShowRepository) :
    ViewModel() {
    private val _tvShowsPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val tvShowsPagingData = _tvShowsPagingData.asStateFlow()

    private val _tvShowDetails = MutableStateFlow<Resource<TvShowDetails>?>(null)
    val tvShowDetails = _tvShowDetails.asStateFlow()


    private val _tvShowCredits = MutableStateFlow<Resource<CastResponse>?>(null)
    val tvShowCredit = _tvShowCredits.asStateFlow()

    private val _tvShowVideos = MutableStateFlow<Resource<MoviePreviewResponse>?>(null)
    val tvShowVideos = _tvShowVideos.asStateFlow()

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


    fun getCredits(tvShowId: String){
        viewModelScope.launch {
            tvShowRepository.getCredits(tvShowId)
                .collectLatest {
                    _tvShowCredits.emit(it)
                }
        }
    }

    fun getVideos(tvShowId: String) {
        viewModelScope.launch {
            tvShowRepository.getTvShowVideos(tvShowId)
                .collectLatest {

                }
        }
    }


    fun getRecommendedTvShows(tvShowId: String): Flow<PagingData<Movie>> {
        return tvShowRepository.getRecommendedTvShows(tvShowId).cachedIn(viewModelScope)
    }

}