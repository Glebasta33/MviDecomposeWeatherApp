package com.github.mvidecomposeweatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherCurrentDTO(
    @SerializedName("current") val current: WeatherDTO
)

data class WeatherDTO(
    @SerializedName("last_updated_epoch") val date: Long,
    @SerializedName("temp_c") val tempC: Float,
    @SerializedName("condition") val condition: ConditionDTO,
)

data class ConditionDTO(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val iconUrl: String
)

