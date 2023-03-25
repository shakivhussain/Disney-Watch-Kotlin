package com.shakiv.husain.disneywatch

import android.app.Application
import com.shakiv.husain.disneywatch.di.AppComponent
import com.shakiv.husain.disneywatch.di.DaggerAppComponent

class DisneyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}