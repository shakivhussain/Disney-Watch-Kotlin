package com.shakiv.husain.disneywatch.di

import android.content.Context
import com.shakiv.husain.disneywatch.MainActivity
import com.shakiv.husain.disneywatch.ui.ui.home.HomeFragment
import com.shakiv.husain.disneywatch.ui.ui.viewMovieDetails.ViewDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: HomeFragment)
    fun inject(mainActivity: ViewDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}