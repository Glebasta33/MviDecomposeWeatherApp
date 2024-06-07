package com.github.mvidecomposeweatherapp.domain.usecase

import com.github.mvidecomposeweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int) = repository.getWeather(cityId)

}