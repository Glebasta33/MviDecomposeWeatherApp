package com.github.mvidecomposeweatherapp.domain.repository

import com.github.mvidecomposeweatherapp.domain.model.City

interface SearchRepository {

    suspend fun search(query: String): List<City>

}