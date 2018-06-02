package com.codecameo.weather.data

import android.content.Context
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.di.ApplicationContext
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.models.WeatherViewModel
import com.codecameo.weather.network.models.address.AddressApiModel
import com.codecameo.weather.network.models.timezone.TimeZoneApiModel
import com.codecameo.weather.network.models.weather.WeatherDetailsApiModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class AppDataProvider @Inject constructor(@ApplicationContext val context : Context, val dbHelper : DbManager, val networkDataManager: NetworkDataManager, val locationDataManager: LocationDataManager): DataManager {
    override fun saveCurLocation(locationEntity: LocationEntity) {
        return dbHelper.saveCurLocation(locationEntity)
    }

    override fun getLocationList(): Flowable<List<LocationViewModel>> {
        return dbHelper.getLocationList()
    }

    override fun getLocationFromAPi(lat: Double, lng: Double): Single<String> {
        return locationDataManager.getLocationFromAPi(lat, lng)
    }

    override fun getLocation(lat: Double, lng: Double): Single<String> {
        return locationDataManager.getLocation(lat, lng)
    }

    override fun getTimeZone(latlong: String, timeStamp: Long): Single<TimeZoneApiModel> {
        return networkDataManager.getTimeZone(latlong, timeStamp)
    }

    override fun getCurrentWeatherUpdate(lat: Double, lng: Double, appId: String): Single<WeatherDetailsApiModel> {
        return networkDataManager.getCurrentWeatherUpdate(lat, lng, appId)
    }

    override fun saveLastSeenWeather(weatherDetailsEntity: WeatherDetailsEntity) {
        return dbHelper.saveLastSeenWeather(weatherDetailsEntity)
    }

    override fun getLastWeatherInfo(): Flowable<WeatherViewModel> {
        return dbHelper.getLastWeatherInfo()
    }
}