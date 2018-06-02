package com.codecameo.weather.data

import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.models.WeatherViewModel
import io.reactivex.Flowable
import io.reactivex.Observable

interface DbManager {
    fun getLastWeatherInfo() : Flowable<WeatherViewModel>
    fun saveLastSeenWeather(weatherDetailsEntity: WeatherDetailsEntity)
    fun getLocationList() : Flowable<List<LocationViewModel>>
    fun saveCurLocation(locationEntity: LocationEntity)
}