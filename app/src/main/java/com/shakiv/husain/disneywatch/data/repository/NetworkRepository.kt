package com.shakiv.husain.disneywatch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.model.PlaceHolder
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {

    private val _products = MutableLiveData<List<PlaceHolder>>()
    val products: LiveData<List<PlaceHolder>>
        get() = _products

    suspend fun getProducts() {

        val result = networkService.getProducts()
        if (result.isSuccessful && result.body().isNullOrEmpty().not()) {
            _products.postValue(result.body())
//            _products.value=(result.body())
        }


    }


}