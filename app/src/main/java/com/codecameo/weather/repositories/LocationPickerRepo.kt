package com.codecameo.weather.repositories

import com.codecameo.weather.data.DataManager
import com.codecameo.weather.dbhelper.entity.LocationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationPickerRepo @Inject constructor(dataManager: DataManager) : BaseRepo(dataManager) {
    fun getLocation(lat: Double, lng: Double): Single<String> {
        return dataManager.getLocation(lat, lng)
    }

    fun saveLocation(locationEntity: LocationEntity) {
        Completable.fromAction { dataManager.saveLocation(locationEntity) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}