package com.github.mvidecomposeweatherapp.presentation.favourite

import com.github.mvidecomposeweatherapp.domain.model.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onClickSearch()

    fun onClickedAddFavourite()

    fun onCityItemClick(city: City)
}