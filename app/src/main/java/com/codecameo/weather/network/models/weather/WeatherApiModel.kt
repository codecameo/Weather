package com.codecameo.weather.network.models.weather

import com.codecameo.weather.DEFAULT_INT
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.WeatherState

data class WeatherApiModel(val id:Int = DEFAULT_INT, val main : String = WeatherState.CLEAR_SKY_DAY, val description : String = EMPTY_STRING, val icon : String = WeatherState.CLEAR_SKY_DAY)