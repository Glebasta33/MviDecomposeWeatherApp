package com.github.mvidecomposeweatherapp.domain.usecase

import com.github.mvidecomposeweatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteCityUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.favouriteCities
}