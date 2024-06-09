package com.github.mvidecomposeweatherapp.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.mvidecomposeweatherapp.domain.model.City
import com.github.mvidecomposeweatherapp.domain.model.Forecast
import com.github.mvidecomposeweatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.github.mvidecomposeweatherapp.domain.usecase.GetForecastUseCase
import com.github.mvidecomposeweatherapp.domain.usecase.ObserveFavouriteStateUseCase
import com.github.mvidecomposeweatherapp.presentation.details.DetailsStore.Intent
import com.github.mvidecomposeweatherapp.presentation.details.DetailsStore.Label
import com.github.mvidecomposeweatherapp.presentation.details.DetailsStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

internal interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data object ClickChangeFavouriteStatus : Intent
    }

    data class State(
        val city: City,
        val isFavourite: Boolean,
        val forecastState: ForecastState
    ) {
        sealed interface ForecastState {
            data object Initial : ForecastState
            data object Loading : ForecastState
            data object Error : ForecastState
            data class Loaded(val forecast: Forecast) : ForecastState
        }
    }

    sealed interface Label {
        data object ClickBack : Label
    }
}

internal class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) {

    fun create(city: City): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
                city = city,
                isFavourite = false,
                forecastState = State.ForecastState.Initial
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavouriteStatusChanged(val isFavourite: Boolean) : Action
        data class ForecastLoaded(val forecast: Forecast) : Action
        data object ForecastStartLoading : Action
        data object ForecastLoadingError : Action
    }

    private sealed interface Msg {
        data class OnFavouriteStatusChanged(val isFavourite: Boolean) : Msg
        data class OnForecastLoaded(val forecast: Forecast) : Msg
        data object OnForecastStartLoading : Msg
        data object OnForecastLoadingError : Msg
    }

    private inner class BootstrapperImpl(private val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavouriteStateUseCase(city.id).collect { isFavourite ->
                    dispatch(Action.FavouriteStatusChanged(isFavourite))
                }
            }
            scope.launch {
                dispatch(Action.ForecastStartLoading)
                try {
                    val forecast = getForecastUseCase(city.id)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastLoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                Intent.ClickChangeFavouriteStatus -> {
                    scope.launch {
                        if (getState().isFavourite) {
                            changeFavouriteStateUseCase.removeFromFavourite(getState().city.id)
                        } else {
                            changeFavouriteStateUseCase.addToFavourite(getState().city)
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteStatusChanged -> {
                    dispatch(Msg.OnFavouriteStatusChanged(action.isFavourite))
                }

                is Action.ForecastLoaded -> {
                    dispatch(Msg.OnForecastLoaded(action.forecast))
                }

                Action.ForecastLoadingError ->
                    dispatch(Msg.OnForecastLoadingError)

                Action.ForecastStartLoading -> {
                    dispatch(Msg.OnForecastStartLoading)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.OnFavouriteStatusChanged -> {
                copy(isFavourite = msg.isFavourite)
            }
            is Msg.OnForecastLoaded -> {
                copy(forecastState = State.ForecastState.Loaded(msg.forecast))
            }
            Msg.OnForecastLoadingError -> {
                copy(forecastState = State.ForecastState.Error)
            }
            Msg.OnForecastStartLoading -> {
                copy(forecastState = State.ForecastState.Loading)
            }
        }
    }
}
