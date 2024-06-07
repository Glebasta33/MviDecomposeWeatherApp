package com.github.mvidecomposeweatherapp.domain.model

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
