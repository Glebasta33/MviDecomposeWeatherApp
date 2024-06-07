package com.github.mvidecomposeweatherapp.domain.repository

import com.github.mvidecomposeweatherapp.domain.model.City
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteCities: Flow<City>

    fun observeIsFavorite(cityId: Int): Flow<Boolean>

    suspend fun addToFavourite(city: City)

    suspend fun removeFromFavourite(cityId: Int)

}