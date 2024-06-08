package com.github.mvidecomposeweatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastDTO(
    @SerializedName("current") val current: WeatherDTO,
    @SerializedName("forecast") val forecastDTO: ForecastDTO
)

data class ForecastDTO(
    @SerializedName("forecastday") val forecastDay: List<DayDTO>
)

data class DayDTO(
    @SerializedName("date_epoch") val date: Long,
    @SerializedName("day") val dayWeatherDTO: DayWeatherDTO
)


data class DayWeatherDTO(
    @SerializedName("avgtemp_c") val tempC: Float,
    @SerializedName("condition") val condition: ConditionDTO,
)




