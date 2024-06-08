package com.github.mvidecomposeweatherapp.data.mapper

import com.github.mvidecomposeweatherapp.data.network.dto.CitySearchDTO
import com.github.mvidecomposeweatherapp.domain.model.City

fun CitySearchDTO.toEntity() = City(id, name, country)