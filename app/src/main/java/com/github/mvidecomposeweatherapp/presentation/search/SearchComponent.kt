package com.github.mvidecomposeweatherapp.presentation.search

import com.github.mvidecomposeweatherapp.domain.model.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun onChangeSearchQuery(query: String)

    fun onClickBack()

    fun onClickSearch()

    fun onClickCity(city: City)
}