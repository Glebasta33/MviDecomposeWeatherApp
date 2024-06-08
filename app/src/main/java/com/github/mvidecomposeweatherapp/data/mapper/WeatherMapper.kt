package com.github.mvidecomposeweatherapp.data.mapper

import com.github.mvidecomposeweatherapp.data.network.dto.WeatherCurrentDTO
import com.github.mvidecomposeweatherapp.data.network.dto.WeatherDTO
import com.github.mvidecomposeweatherapp.data.network.dto.WeatherForecastDTO
import com.github.mvidecomposeweatherapp.domain.model.Forecast
import com.github.mvidecomposeweatherapp.domain.model.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDTO.toEntity(): Weather = current.toEntity()

fun WeatherDTO.toEntity(): Weather = Weather(
    tempC = tempC,
    conditionText = condition.text,
    conditionIconUrl = condition.iconUrl.currectImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDTO.toEntity() = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecastDTO.forecastDay.drop(1).map { dayDto ->
        with(dayDto.dayWeatherDTO) {
            Weather(
                tempC = tempC,
                conditionText = condition.text,
                conditionIconUrl = condition.iconUrl.currectImageUrl(),
                date = dayDto.date.toCalendar()
            )
        }
    }
)

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.currectImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)