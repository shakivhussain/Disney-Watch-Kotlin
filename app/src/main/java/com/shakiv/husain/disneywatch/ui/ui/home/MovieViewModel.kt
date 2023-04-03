package com.shakiv.husain.disneywatch.ui.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.husain.disneywatch.data.model.cast.CastResponse
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.data.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {


    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return repository.getPopularMovies().cachedIn(viewModelScope)
    }

    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return repository.getTrendingMovies().cachedIn(viewModelScope)
    }

    fun getUpComingMovies():Flow<PagingData<Movie>>{
        return repository.getUpcomingMovies()
    }

    fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> {
        return repository.getMovieDetails(movieId = movieId)
    }


    fun getMovieImages(movieId: String) : Flow<Resource<ImageResponse>>{
        return repository.getMovieImages(movieId)
    }

    fun getCasts(movieId: String): Flow<Resource<CastResponse>> {
        return repository.getCasts(movieId)
    }

}