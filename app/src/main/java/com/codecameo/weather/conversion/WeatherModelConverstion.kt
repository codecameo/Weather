package com.codecameo.weather.conversion

import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.WeatherViewModel
import com.codecameo.weather.network.models.weather.WeatherApiModel
import com.codecameo.weather.network.models.weather.WeatherDetailsApiModel
import kotlin.math.roundToInt

fun getWeatherViewModel(weatherDetails: WeatherDetailsApiModel) : WeatherViewModel{
    val windInfo = weatherDetails.wind?.let { getWindInfo(it.speed, it.degree) }
    val weather = if(weatherDetails.weather.size> 0 ) weatherDetails.weather.get(0) else WeatherApiModel()
    val weatherViewModel = WeatherViewModel(weatherDetails.id)
    weatherViewModel.temp = weatherDetails.main.temp.roundToInt()
    weatherViewModel.icon = weather.icon
    weatherViewModel.condition = weather.main
    return weatherViewModel
}

fun getWeatherDbModel(weatherDetails: WeatherDetailsApiModel, entity: WeatherDetailsEntity) : WeatherDetailsEntity {

    val windInfo = weatherDetails.wind?.let { getWindInfo(it.speed, it.degree) }
    val weather = if(weatherDetails.weather.size> 0 ) weatherDetails.weather.get(0) else WeatherApiModel()

    entity.condition = weather.main
    entity.temp = weatherDetails.main.temp.roundToInt()
    entity.icon = weather.icon
    entity.humidity = weatherDetails.main.humidity
    entity.pressure = weatherDetails.main.pressure
    entity.maxTemp = weatherDetails.main.temp_max.roundToInt()
    entity.minTemp = weatherDetails.main.temp_min.roundToInt()
    entity.wind = windInfo
    entity.sunset = weatherDetails.sys.sunset
    entity.sunrise = weatherDetails.sys.sunrise
    return entity
}