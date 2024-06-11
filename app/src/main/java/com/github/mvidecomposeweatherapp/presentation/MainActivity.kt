package com.github.mvidecomposeweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.github.mvidecomposeweatherapp.WeatherApp
import com.github.mvidecomposeweatherapp.presentation.root.RootComponentImpl
import com.github.mvidecomposeweatherapp.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: RootComponentImpl.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as WeatherApp).applicationComponent.inject(this)
        val rootComponent = rootComponentFactory.create(defaultComponentContext())

        setContent {
            RootContent(component = rootComponent)
        }
    }
}