package com.codecameo.weather.repositories

import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.data.DataManager
import io.reactivex.Single
import javax.inject.Inject

class LocationPickerRepo @Inject constructor(dataManager: DataManager) : BaseRepo(dataManager) {
    fun getLocation(lat: Double, lng: Double): Single<String> {
        return dataManager.getLocation(lat, lng)
    }
}