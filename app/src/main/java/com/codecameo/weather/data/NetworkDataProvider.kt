package com.codecameo.weather.data

import android.util.Log
import com.codecameo.weather.network.apiclient.GoogleApiClient
import com.codecameo.weather.network.apiclient.WeatherApiClient
import com.codecameo.weather.network.interfaces.GoogleApiService
import com.codecameo.weather.network.interfaces.WeatherApiService
import com.codecameo.weather.network.models.address.AddressApiModel
import com.codecameo.weather.network.models.timezone.TimeZoneApiModel
import com.codecameo.weather.network.models.weather.WeatherDetailsApiModel
import io.reactivex.Single
import javax.inject.Inject

class NetworkDataProvider @Inject constructor(val weatherApiService: WeatherApiService, val googleApiService: GoogleApiService) : NetworkDataManager {
    override fun getTimeZone(latlong: String, timeStamp: Long): Single<TimeZoneApiModel> {
        return googleApiService.getTimeZone(latlong, timeStamp)
    }

    override fun getCurrentWeatherUpdate(lat: Double, lng: Double, appId: String): Single<WeatherDetailsApiModel> {
        return weatherApiService.getCurrentWeather(lat, lng, appId)
    }
}