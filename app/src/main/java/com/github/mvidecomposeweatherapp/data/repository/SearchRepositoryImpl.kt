package com.github.mvidecomposeweatherapp.data.repository

import com.github.mvidecomposeweatherapp.data.mapper.toEntity
import com.github.mvidecomposeweatherapp.data.network.api.ApiService
import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).map { it.toEntity() }
    }
}