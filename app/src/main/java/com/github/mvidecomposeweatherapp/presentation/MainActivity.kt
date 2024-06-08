package com.github.mvidecomposeweatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.mvidecomposeweatherapp.data.network.api.ApiFactory
import com.github.mvidecomposeweatherapp.presentation.ui.theme.MviDecomposeWeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.IO).launch {
            val currentWeather = apiService.loadCurrentWeather("Moscow")
            val forecast = apiService.loadForecast("Moscow")
            val cities = apiService.searchCity("Moscow")

            Log.d("MyTest", "$currentWeather " +
                    "\n $forecast" +
                    "\n $cities")
        }
        setContent {
            MviDecomposeWeatherAppTheme {

            }
        }
    }
}