package com.github.mvidecomposeweatherapp.data.repository

import com.github.mvidecomposeweatherapp.data.local.db.FavouriteCitiesDao
import com.github.mvidecomposeweatherapp.data.mapper.toDbModel
import com.github.mvidecomposeweatherapp.data.mapper.toEntities
import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteCitiesDao: FavouriteCitiesDao
): FavouriteRepository {
    override val favouriteCities: Flow<List<City>> = favouriteCitiesDao.getFavouriteCities()
        .map { it.toEntities() }

    override fun observeIsFavorite(cityId: Int): Flow<Boolean> = favouriteCitiesDao
        .observeIsFavourite(cityId)

    override suspend fun addToFavourite(city: City) {
        favouriteCitiesDao.addToFavourite(city.toDbModel())
    }

    override suspend fun removeFromFavourite(cityId: Int) {
        favouriteCitiesDao.removeFromFavourite(cityId)
    }
}