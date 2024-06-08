package com.github.mvidecomposeweatherapp.data.mapper

import com.github.mvidecomposeweatherapp.data.local.model.CityDbModel
import com.github.mvidecomposeweatherapp.domain.model.City

fun City.toDbModel() = CityDbModel(id, name, country)

fun CityDbModel.toEntity() = City(id, name, country)

fun List<CityDbModel>.toEntities(): List<City> = map { it.toEntity() }