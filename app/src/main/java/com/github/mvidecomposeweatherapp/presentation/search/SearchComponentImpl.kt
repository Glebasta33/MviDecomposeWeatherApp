package com.github.mvidecomposeweatherapp.presentation.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.presentation.utils.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchComponentImpl @AssistedInject constructor(
    private val searchStoreFactory: SearchStoreFactory,
    @Assisted("openReason") openReason: OpenReason,
    @Assisted("onBackClick") onBackClick: () -> Unit,
    @Assisted("onSavedToFavouriteClick") onSavedToFavouriteClick: () -> Unit,
    @Assisted("onOpenForecastClick") onOpenForecastClick: (City) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { searchStoreFactory.create(openReason) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State> = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect { label ->
                when (label) {
                    SearchStore.Label.ClickBack -> onBackClick()
                    is SearchStore.Label.OpenForecast -> onOpenForecastClick(label.city)
                    SearchStore.Label.SavedToFavourite -> onSavedToFavouriteClick()
                }
            }
        }
    }

    override fun onChangeSearchQuery(query: String) {
        store.accept(SearchStore.Intent.ChangeSearchQuery(query))
    }

    override fun onClickBack() {
        store.accept(SearchStore.Intent.ClickBack)
    }

    override fun onClickSearch() {
        store.accept(SearchStore.Intent.ClickSearch)
    }

    override fun onClickCity(city: City) {
        store.accept(SearchStore.Intent.ClickCity(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("openReason") openReason: OpenReason,
            @Assisted("onBackClick") onBackClick: () -> Unit,
            @Assisted("onSavedToFavouriteClick") onSavedToFavouriteClick: () -> Unit,
            @Assisted("onOpenForecastClick") onOpenForecastClick: (City) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): SearchComponentImpl
    }
}