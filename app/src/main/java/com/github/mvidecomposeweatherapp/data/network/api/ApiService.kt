package com.github.mvidecomposeweatherapp.data.network.api

import com.github.mvidecomposeweatherapp.data.network.dto.CitySearchDTO
import com.github.mvidecomposeweatherapp.data.network.dto.WeatherCurrentDTO
import com.github.mvidecomposeweatherapp.data.network.dto.WeatherForecastDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json?key=X")
    suspend fun loadCurrentWeather(
        @Query("q") query: String
    ): WeatherCurrentDTO

    @GET("forecast.json?key=X")
    suspend fun loadForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDTO

    @GET("search.json?key=X")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CitySearchDTO>
}