package com.shakiv.husain.disneywatch.di

import com.shakiv.husain.disneywatch.data.api.NetworkService
import com.shakiv.husain.disneywatch.util.Constants
import com.shakiv.husain.disneywatch.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.migration.DisableInstallInCheck
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
            .build()
    }


    @Singleton
    @Provides
    fun provideNetworkService(builder: Retrofit): NetworkService {
        return builder.create(NetworkService::class.java)
    }

}