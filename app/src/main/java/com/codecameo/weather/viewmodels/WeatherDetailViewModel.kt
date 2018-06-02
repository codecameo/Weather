package com.codecameo.weather.viewmodels

import android.location.Address
import android.location.Geocoder
import com.codecameo.weather.DEFAULT_DOUBLE
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.models.WeatherViewModel
import com.codecameo.weather.repositories.WeatherDetailsRepo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import java.io.IOException
import javax.inject.Inject

class WeatherDetailViewModel @Inject constructor(private val mRepo : WeatherDetailsRepo) : BaseViewModel() {

    var mLat : Double = 0.0 //40.711389//23.749466
    var mLng : Double = 0.0 //-74.010874//90.383610
    var mLocation : String = EMPTY_STRING

    fun updateWeatherDetails() : Completable{
         return mRepo.updateWeatherLocation(mLat, mLng)
    }

    fun getLastSeenWeatherDetails() : Flowable<WeatherViewModel> {
        return mRepo.getLastSeenWeather()
    }

    fun isLocationSet(): Boolean {
        return mLat != DEFAULT_DOUBLE || mLng != DEFAULT_DOUBLE
    }

    fun getSavedLocationList(): Flowable<List<LocationViewModel>> {
        return mRepo.getSavedLocationList()
    }
}