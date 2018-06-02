package com.codecameo.weather.network.models.weather

data class WeatherMainApiModel(val temp : Double, val temp_min : Double, val temp_max : Double, val pressure : Double, val humidity: Int)