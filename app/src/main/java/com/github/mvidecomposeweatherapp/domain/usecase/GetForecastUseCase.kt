package com.github.mvidecomposeweatherapp.domain.usecase

import com.github.mvidecomposeweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int) = repository.getForecast(cityId)

}