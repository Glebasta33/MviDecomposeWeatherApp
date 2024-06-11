package com.github.mvidecomposeweatherapp.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.github.mvidecomposeweatherapp.presentation.details.DetailsContent
import com.github.mvidecomposeweatherapp.presentation.favourite.FavouriteContent
import com.github.mvidecomposeweatherapp.presentation.search.SearchContent
import com.github.mvidecomposeweatherapp.presentation.ui.theme.MviDecomposeWeatherAppTheme

@Composable
fun RootContent(component: RootComponent) {
    MviDecomposeWeatherAppTheme {
        Children(stack = component.stack) {
            when(val instance = it.instance) {
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instance.component)
                }
                is RootComponent.Child.Favourite -> {
                    FavouriteContent(component = instance.component)
                }
                is RootComponent.Child.Search -> {
                    SearchContent(component = instance.component)
                }
            }
        }
    }
}