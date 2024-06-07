package com.github.mvidecomposeweatherapp.domain.usecase

import com.github.mvidecomposeweatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String) = repository.search(query)

}