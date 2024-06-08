package com.github.mvidecomposeweatherapp.data.repository

import com.github.mvidecomposeweatherapp.data.mapper.toEntity
import com.github.mvidecomposeweatherapp.data.network.api.ApiService
import com.github.mvidecomposeweatherapp.domain.model.Forecast
import com.github.mvidecomposeweatherapp.domain.model.Weather
import com.github.mvidecomposeweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }

    private companion object {
        const val PREFIX_CITY_ID = "id:"
    }
}