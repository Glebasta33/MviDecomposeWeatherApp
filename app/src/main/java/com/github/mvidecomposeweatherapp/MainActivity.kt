package com.github.mvidecomposeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.mvidecomposeweatherapp.ui.theme.MviDecomposeWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviDecomposeWeatherAppTheme {

            }
        }
    }
}