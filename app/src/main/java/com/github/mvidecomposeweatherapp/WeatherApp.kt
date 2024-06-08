package com.github.mvidecomposeweatherapp

import android.app.Application
import com.github.mvidecomposeweatherapp.di.ApplicationComponent
import com.github.mvidecomposeweatherapp.di.DaggerApplicationComponent

class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}