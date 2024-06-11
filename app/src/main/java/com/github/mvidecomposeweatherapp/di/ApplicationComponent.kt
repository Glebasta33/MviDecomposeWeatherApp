package com.github.mvidecomposeweatherapp.di

import android.content.Context
import com.github.mvidecomposeweatherapp.di.annotation.ApplicationScope
import com.github.mvidecomposeweatherapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        PresentationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}