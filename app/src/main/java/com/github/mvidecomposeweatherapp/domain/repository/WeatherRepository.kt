package com.github.mvidecomposeweatherapp.domain.repository

import com.github.mvidecomposeweatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int)

}