package com.shakiv.husain.disneywatch.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.disneywatch.data.model.Movie
import com.shakiv.husain.disneywatch.data.model.PlaceHolder
import com.shakiv.husain.disneywatch.data.repository.NetworkRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

//    val productsLiveData: LiveData<List<PlaceHolder>>
//        get() = repository.products

//    init {
//
//        viewModelScope.launch {
//           repository.getTopRatedMovies()
//
//
//        }
//    }

    fun getTopRatedMovies() = flow<List<Movie>> {
        repository.getTopRatedMovies()

    }

}