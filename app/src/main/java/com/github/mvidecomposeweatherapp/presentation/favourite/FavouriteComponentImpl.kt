package com.github.mvidecomposeweatherapp.presentation.favourite

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

class FavouriteComponentImpl @AssistedInject constructor(
    private val favouriteStoreFactory: FavouriteStoreFactory,
    @Assisted("onCityItemClicked") onCityItemClicked: (City) -> Unit,
    @Assisted("onAddToFavouriteClicked") onAddToFavouriteClicked: () -> Unit,
    @Assisted("onSearchClicked") onSearchClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : FavouriteComponent, ComponentContext by componentContext {

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

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onCityItemClicked") onCityItemClicked: (City) -> Unit,
            @Assisted("onAddToFavouriteClicked") onAddToFavouriteClicked: () -> Unit,
            @Assisted("onSearchClicked") onSearchClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): FavouriteComponentImpl
    }
}