package com.shakiv.husain.disneywatch.di

import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.util.ApiConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@DisableInstallInCheck
@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(loggingInterceptor())
            .build()
    }


    @Singleton
    @Provides
    fun provideNetworkService(builder: Retrofit): NetworkService {
        return builder.create(NetworkService::class.java)
    }

    private fun loggingInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

}