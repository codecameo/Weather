package com.codecameo.weather.data

import com.codecameo.weather.LAST_SEEN_WEATHER_ID
import com.codecameo.weather.dbhelper.database.WeatherDB
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.models.WeatherViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class DbProvider @Inject constructor(val weatherDb : WeatherDB) : DbManager{
    override fun saveLocation(locationEntity: LocationEntity) {
        return weatherDb.getLocationListDao().insert(locationEntity)
    }

    override fun getLocationList(): Flowable<List<LocationViewModel>> {
        return weatherDb.getLocationListDao().getAllLocations()
    }

    override fun saveLastSeenWeather(weatherDetailsEntity: WeatherDetailsEntity) {
        weatherDb.getWeatherDetailsDao().insert(weatherDetailsEntity)
    }

    override fun getLastWeatherInfo(): Flowable<WeatherViewModel> {
        return weatherDb.getWeatherDetailsDao().getLastSeenWeather(LAST_SEEN_WEATHER_ID)
    }
}