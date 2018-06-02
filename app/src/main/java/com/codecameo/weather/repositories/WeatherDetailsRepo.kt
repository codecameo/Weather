package com.codecameo.weather.repositories

import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.LAST_SEEN_WEATHER_ID
import com.codecameo.weather.conversion.getWeatherDbModel
import com.codecameo.weather.data.DataManager
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.models.WeatherViewModel
import com.codecameo.weather.network.ApiConstant
import com.codecameo.weather.network.models.address.AddressApiModel
import com.codecameo.weather.network.models.timezone.TimeZoneApiModel
import com.codecameo.weather.network.models.weather.WeatherDetailsApiModel
import com.codecameo.weather.utils.isListEmpty
import com.codecameo.weather.utils.parseLocation
import com.codecameo.weather.utils.showToast
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.ln

class WeatherDetailsRepo @Inject constructor(dataManager : DataManager)  : BaseRepo(dataManager) {

    fun updateWeather(lat: Double, lng: Double, location : String) {
        var weatherDetailsEntity = WeatherDetailsEntity(LAST_SEEN_WEATHER_ID)
        weatherDetailsEntity.location = location
        val timeStamp = System.currentTimeMillis()/1000
        Observable.zip(dataManager.getCurrentWeatherUpdate(lat, lng, ApiConstant.APP_ID_WEATHER).toObservable(), dataManager.getTimeZone("$lat,$lng", timeStamp).toObservable(),
                object : BiFunction<WeatherDetailsApiModel, TimeZoneApiModel, WeatherDetailsEntity>{
                    override fun apply(weatherDetailsApiModel: WeatherDetailsApiModel, timeZoneApiModel: TimeZoneApiModel): WeatherDetailsEntity {
                        getWeatherDbModel(weatherDetailsApiModel, weatherDetailsEntity)
                        timeZoneApiModel?.let {
                            weatherDetailsEntity.timeZoneId = it.timeZoneId?: EMPTY_STRING
                        }
                        return weatherDetailsEntity
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(object : Consumer<WeatherDetailsEntity>{
                    override fun accept(weatherDetailsEntity: WeatherDetailsEntity) {
                        saveLastSeenWeather(weatherDetailsEntity)
                    }
                })
    }

    fun updateWeatherLocation(lat: Double, lng: Double) : Completable {
        return Completable.create {
            val timeStamp = System.currentTimeMillis()/1000
            Observable.zip(
                dataManager.getCurrentWeatherUpdate(lat, lng, ApiConstant.APP_ID_WEATHER).toObservable(),
                dataManager.getLocation(lat, lng).toObservable(),
                dataManager.getTimeZone("$lat,$lng", timeStamp).toObservable(),
                object : Function3<WeatherDetailsApiModel, String, TimeZoneApiModel, WeatherDetailsEntity>{
                    override fun apply(weatherDetailsApiModel: WeatherDetailsApiModel, location: String, timeZoneApiModel: TimeZoneApiModel): WeatherDetailsEntity {
                        val entity = WeatherDetailsEntity(LAST_SEEN_WEATHER_ID)
                        entity.let { getWeatherDbModel(weatherDetailsApiModel, it) }
                        entity.location = location
                        timeZoneApiModel?.let {
                            entity.timeZoneId = it.timeZoneId?: EMPTY_STRING
                        }
                        return entity
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(object : Consumer<WeatherDetailsEntity>{
                    override fun accept(weatherDetailsEntity: WeatherDetailsEntity) {
                        saveLastSeenWeather(weatherDetailsEntity)
                        saveCurrentLocation(LocationEntity(LAST_SEEN_WEATHER_ID, lat, lng, weatherDetailsEntity.location))
                        it.onComplete()
                    }
                }, object : Consumer<Throwable>{
                    override fun accept(t: Throwable) {
                        it.onError(t)
                    }
                })
        }

    }

    private fun saveCurrentLocation(locationEntity: LocationEntity) {
        return dataManager.saveCurLocation(locationEntity)
    }

    fun getLastSeenWeather(): Flowable<WeatherViewModel> {
        return dataManager.getLastWeatherInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveLastSeenWeather(weatherDetailsEntity: WeatherDetailsEntity){
        dataManager.saveLastSeenWeather(weatherDetailsEntity)
    }

    fun getSavedLocationList(): Flowable<List<LocationViewModel>> {
        return dataManager.getLocationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}


/*Observable.zip(dataManager.getCurrentWeatherUpdate(lat, lng, ApiConstant.APP_ID_WEATHER).toObservable(), dataManager.getLocation("$lat,$lng").toObservable(),
                object : BiFunction<WeatherDetailsApiModel, AddressApiModel, WeatherDetailsEntity> {
                    override fun apply(weatherDetailsApiModel: WeatherDetailsApiModel, addressApiModel: AddressApiModel): WeatherDetailsEntity {
                        val entity = WeatherDetailsEntity(LAST_SEEN_WEATHER_ID)
                        entity.let { getWeatherDbModel(weatherDetailsApiModel, it) }
                        entity.let { if(!isListEmpty(addressApiModel.results)) it.location = parseLocation(addressApiModel.results.get(0)) }
                        return entity
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(object : Consumer<WeatherDetailsEntity>{
                    override fun accept(weatherDetailsEntity: WeatherDetailsEntity) {
                        saveLastSeenWeather(weatherDetailsEntity)
                    }
                })*/

/*dataManager.getCurrentWeatherUpdate(lat, lng, ApiConstant.APP_ID_WEATHER)
         .subscribeOn(Schedulers.io())
         .observeOn(Schedulers.computation())
         .map {response : WeatherDetailsApiModel ->
             response?.let { getWeatherDbModel(it, weatherDetailsEntity) }
         }.subscribe(object : Consumer<WeatherDetailsEntity>{
            override fun accept(weatherDetailsEntity: WeatherDetailsEntity) {
                saveLastSeenWeather(weatherDetailsEntity)
            }
        })*/