package com.shakiv.husain.disneywatch.di

import androidx.lifecycle.ViewModel
import com.shakiv.husain.disneywatch.presentation.ui.home.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@DisableInstallInCheck
abstract class ViewModelModule {

    @Binds
    @ClassKey(MovieViewModel::class)
    @IntoMap
    abstract fun mainViewModel(movieViewModel: MovieViewModel) : ViewModel

}