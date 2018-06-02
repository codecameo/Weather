package com.codecameo.weather.data

import com.codecameo.weather.network.models.address.AddressApiModel
import com.codecameo.weather.network.models.timezone.TimeZoneApiModel
import com.codecameo.weather.network.models.weather.WeatherDetailsApiModel
import io.reactivex.Single

interface NetworkDataManager {
    fun getCurrentWeatherUpdate(lat : Double, lng : Double, appId : String) : Single<WeatherDetailsApiModel>
    fun getTimeZone(latlong : String, timeStamp : Long) : Single<TimeZoneApiModel>
}