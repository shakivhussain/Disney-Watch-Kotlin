package com.shakiv.husain.disneywatch.di

import androidx.lifecycle.ViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.CollectionViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.MovieViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.TvShowViewModel
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
    abstract fun mainViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @ClassKey(CollectionViewModel::class)
    @IntoMap
    abstract fun collectionViewModel(collectionViewModel: CollectionViewModel): ViewModel

    @Binds
    @ClassKey(TvShowViewModel::class)
    @IntoMap
    abstract fun tvShowViewModel(tvShowViewModel: TvShowViewModel): ViewModel

}