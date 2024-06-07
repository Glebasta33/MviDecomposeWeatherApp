package com.github.mvidecomposeweatherapp.domain.usecase

import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    suspend fun addToFavourite(city: City) = repository.addToFavourite(city)

    suspend fun addToFavourite(cityId: Int) = repository.removeFromFavourite(cityId)

}