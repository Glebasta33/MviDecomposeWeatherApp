package com.github.mvidecomposeweatherapp.presentation.utils

import kotlin.math.roundToInt

fun Float.tempToFormattedString(): String = "${roundToInt()}°C"