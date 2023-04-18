package com.shakiv.husain.disneywatch.ui.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.husain.disneywatch.data.model.image.ImageResponse
import com.shakiv.husain.disneywatch.data.model.movie.Movie
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.data.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CollectionViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
) : ViewModel() {

    private val _collectionPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val collectionPagingSource = _collectionPagingData.asStateFlow()


    fun searchCollections(query: String) {
        viewModelScope.launch {
            collectionRepository
                .searchCollection(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _collectionPagingData.emit(it)
                }
        }
    }

    fun getCollectionImages(collectionId: String): Flow<Resource<ImageResponse>> {
        return collectionRepository.getCollectionsImages(collectionId)
    }

}