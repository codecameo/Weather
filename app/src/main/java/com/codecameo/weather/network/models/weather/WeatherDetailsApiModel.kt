package com.codecameo.weather.network.models.weather

data class WeatherDetailsApiModel(val id: Int, val weather: List<WeatherApiModel>, val wind : WindApiModel, val main: WeatherMainApiModel, val sys : WeatherSysApiModel)