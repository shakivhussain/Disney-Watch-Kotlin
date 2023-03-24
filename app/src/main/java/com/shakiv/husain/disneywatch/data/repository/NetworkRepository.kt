package com.shakiv.husain.disneywatch.data.repository

import android.util.Log
import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.data.model.Movie
import com.shakiv.husain.disneywatch.util.Constants.BASE_URL
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {

//    private val _products = MutableLiveData<List<PlaceHolder>>()
//    val products: LiveData<List<PlaceHolder>>
//        get() = _products

    suspend fun getTopRatedMovies() = flow<List<Movie>> {
        Log.d("GetResponse","View Model Response : 2")

        try {
            NetworkRequest.process {
                networkService.getTopRatedMovies(BASE_URL,1)
            }.run {
                when (this) {
                    is ApiResponse.Success -> {
                        Log.d("Response","Reponse : ${results}")

                        results?.data ?: throw Exception("Error Fetching Top Movies.")
                    }
                    is ApiResponse.Failure -> {

                        throw Exception("Error Fetching Top Movies.")

                    }
                }


            }
        } catch (e: Exception) {
            Log.d("GetResponse","View Model Response : ${e.printStackTrace()}")
            e.printStackTrace()
        }




//        val result = networkService.getTopRatedMovies()
//        if (result.isSuccessful && result.body().isNullOrEmpty().not()) {
//            _products.postValue(result.body())
////            _products.value=(result.body())
//        }


    }


}