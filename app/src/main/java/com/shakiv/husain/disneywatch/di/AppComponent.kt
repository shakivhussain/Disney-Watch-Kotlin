package com.shakiv.husain.disneywatch.di

import android.content.Context
import com.shakiv.husain.disneywatch.MainActivity
import com.shakiv.husain.disneywatch.presentation.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}