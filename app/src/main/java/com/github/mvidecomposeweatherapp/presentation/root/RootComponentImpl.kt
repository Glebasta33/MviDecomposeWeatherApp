package com.github.mvidecomposeweatherapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.presentation.details.DetailsComponentImpl
import com.github.mvidecomposeweatherapp.presentation.favourite.FavouriteComponentImpl
import com.github.mvidecomposeweatherapp.presentation.search.OpenReason
import com.github.mvidecomposeweatherapp.presentation.search.SearchComponentImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class RootComponentImpl @AssistedInject constructor(
    private val detailsComponentFactory: DetailsComponentImpl.Factory,
    private val favouriteComponentFactory: FavouriteComponentImpl.Factory,
    private val searchComponentFactory: SearchComponentImpl.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favourite,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is Config.Details -> {
            val component = detailsComponentFactory.create(
                city = config.city,
                onBackClick = {
                    navigation.pop()
                },
                componentContext = componentContext
            )
            RootComponent.Child.Details(component)
        }

        Config.Favourite -> {
            val component = favouriteComponentFactory.create(
                onCityItemClicked = { city ->
                    navigation.push(Config.Details(city))
                },
                onAddToFavouriteClicked = {
                    navigation.push(Config.Search(OpenReason.ADD_TO_FAVOURITE))
                },
                onSearchClicked = {
                    navigation.push(Config.Search(OpenReason.REGULAR_SEARCH))
                },
                componentContext = componentContext
            )
            RootComponent.Child.Favourite(component)
        }

        is Config.Search -> {
            val component = searchComponentFactory.create(
                openReason = config.openReason,
                onBackClick = {
                    navigation.pop()
                },
                onSavedToFavouriteClick = {
                    navigation.pop()
                },
                onOpenForecastClick = { city ->
                    navigation.push(Config.Details(city))
                },
                componentContext = componentContext
            )
            RootComponent.Child.Search(component)
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object Favourite : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config

        @Parcelize
        data class Details(val city: City) : Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): RootComponentImpl
    }
}