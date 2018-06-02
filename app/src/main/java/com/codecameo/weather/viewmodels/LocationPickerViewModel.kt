package com.codecameo.weather.viewmodels

import android.location.Geocoder
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.repositories.LocationPickerRepo
import com.codecameo.weather.repositories.WeatherDetailsRepo
import io.reactivex.Single
import javax.inject.Inject

class LocationPickerViewModel @Inject constructor(private val mRepo : LocationPickerRepo) : BaseViewModel() {

    var mLat : Double = 0.0 //40.711389//23.749466
    var mLng : Double = 0.0 //-74.010874//90.383610
    var mLocation : String = EMPTY_STRING

    fun getLocation() : Single<String> {
        return mRepo.getLocation(mLat, mLng)
    }

    fun saveLocation(locationEntity: LocationEntity) {
        mRepo.saveLocation(locationEntity)
    }
}