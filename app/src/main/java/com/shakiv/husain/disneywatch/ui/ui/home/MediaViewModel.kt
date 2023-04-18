package com.shakiv.husain.disneywatch.ui.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.model.videos.MoviePreviewResponse
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.data.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MediaViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _moviesPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviesPagingData = _moviesPagingData.asStateFlow()

    private val _tvShowsPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val tvShowsPagingData = _tvShowsPagingData.asStateFlow()


    private val _collectionPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val collectionPagingSource = _collectionPagingData.asStateFlow()

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return repository.getPopularMovies().cachedIn(viewModelScope)
    }

    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return repository.getTrendingMovies().cachedIn(viewModelScope)
    }

    fun getUpComingMovies(): Flow<PagingData<Movie>> {
        return repository.getUpcomingMovies().cachedIn(viewModelScope)
    }

    fun getRecommended(movieId: String): Flow<PagingData<Movie>> {
        return repository.getRecommendedMovies(movieId = movieId).cachedIn(viewModelScope)
    }

    fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> {
        return repository.getMovieDetails(movieId = movieId)
    }


    fun getCollectionDetails(collectionId : String ) : Flow<Resource<MovieDetails>>{
        return repository.getCollectionDetails(collectionId)
    }

    fun getMovieImages(movieId: String): Flow<Resource<ImageResponse>> {
        return repository.getMovieImages(movieId)
    }

    /**
     * Retrieves the cast details of a movie identified by the given [movieId].
     * @param movieId The ID of the movie to retrieve the cast details for.
     * @return A flow of [Resource] that emits the cast details of the movie.
     */
    fun getCasts(movieId: String): Flow<Resource<CastResponse>> {
        return repository.getCasts(movieId)
    }

    fun getMoviesPreview(movieId: String): Flow<Resource<MoviePreviewResponse>> {
        return repository.getMovieVideos(movieId)
    }


    fun searchMovies(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _moviesPagingData.emit(it)
                }
        }
    }


    fun searchTvShow(query: String) {
        viewModelScope.launch {
            repository
                .searchTvShows(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _tvShowsPagingData.emit(it)
                }
        }
    }

    fun searchCollections(query: String) {
        viewModelScope.launch {
            repository
//                .searchCollection(query)
//                .cachedIn(viewModelScope)
//                .collectLatest {
//                    _collectionPagingData.emit(it)
//                }
        }
    }


//    fun getCollectionImages(collectionId: String): Flow<Resource<ImageResponse>> {
////        return repository.getCollectionsImages(collectionId)
//    }

}