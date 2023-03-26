package com.shakiv.husain.disneywatch.ui.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.husain.disneywatch.data.model.Movie
import com.shakiv.husain.disneywatch.data.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {


    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return repository.getPopularMovies().cachedIn(viewModelScope)
    }

    fun getTrendingMovies():Flow<PagingData<Movie>>{
        return repository.getTrendingMovies().cachedIn(viewModelScope)
    }


}