package com.github.mvidecomposeweatherapp.presentation.favourite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.presentation.utils.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteComponentImpl @Inject constructor(
    private val favouriteStoreFactory: FavouriteStoreFactory,
    onCityItemClicked: (City) -> Unit,
    onAddToFavouriteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    componentContext: ComponentContext
) : FavoriteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { favouriteStoreFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State> = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect { label ->
                when (label) {
                    is FavouriteStore.Label.OnCityItemClicked -> onCityItemClicked(label.city)
                    FavouriteStore.Label.OnClickSearch -> onSearchClicked()
                    FavouriteStore.Label.OnClickToFavourite -> onAddToFavouriteClicked()
                }
            }
        }
    }

    override fun onClickSearch() {
        store.accept(FavouriteStore.Intent.ClickSearch)
    }

    override fun onClickedAddFavourite() {
        store.accept(FavouriteStore.Intent.ClickAddToFavourite)
    }

    override fun onCityItemClick(city: City) {
        store.accept(FavouriteStore.Intent.CityItemClicked(city))
    }
}