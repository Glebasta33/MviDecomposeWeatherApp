package com.github.mvidecomposeweatherapp.di

import android.content.Context
import com.github.mvidecomposeweatherapp.data.local.db.FavouriteCitiesDao
import com.github.mvidecomposeweatherapp.data.local.db.FavouriteDatabase
import com.github.mvidecomposeweatherapp.data.network.api.ApiFactory
import com.github.mvidecomposeweatherapp.data.network.api.ApiService
import com.github.mvidecomposeweatherapp.data.repository.FavouriteRepositoryImpl
import com.github.mvidecomposeweatherapp.data.repository.SearchRepositoryImpl
import com.github.mvidecomposeweatherapp.data.repository.WeatherRepositoryImpl
import com.github.mvidecomposeweatherapp.di.annotation.ApplicationScope
import com.github.mvidecomposeweatherapp.domain.repository.FavouriteRepository
import com.github.mvidecomposeweatherapp.domain.repository.SearchRepository
import com.github.mvidecomposeweatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[Binds ApplicationScope]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[Binds ApplicationScope]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[Binds ApplicationScope]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {
        @[Provides ApplicationScope]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[Provides ApplicationScope]
        fun provideFavouriteDatabase(context: Context): FavouriteDatabase {
            return FavouriteDatabase.getInstance(context)
        }

        @[Provides ApplicationScope]
        fun provideFavouriteCitiesDao(database: FavouriteDatabase): FavouriteCitiesDao {
            return database.favouriteCitiesDao()
        }
    }
}