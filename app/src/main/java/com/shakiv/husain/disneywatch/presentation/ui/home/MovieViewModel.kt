package com.shakiv.husain.disneywatch.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.disneywatch.data.model.Movie
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.data.repository.NetworkRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {


    private val _topRatedMovies = MutableSharedFlow<Resource<List<Movie>>>()
    val topRatedMovies = _topRatedMovies.asSharedFlow()

    fun getTopRatedMovies() {
        viewModelScope.launch {
            repository.getTopRatedMovies().collectLatest {
                _topRatedMovies.emit(it)
            }
        }
    }

}