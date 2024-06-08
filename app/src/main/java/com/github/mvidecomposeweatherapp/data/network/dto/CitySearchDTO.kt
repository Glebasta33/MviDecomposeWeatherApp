package com.github.mvidecomposeweatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CitySearchDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String
)