package com.codecameo.weather.dbhelper.dao

import android.arch.persistence.room.*
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.models.WeatherViewModel
import io.reactivex.Flowable

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createLocationIfNotExists(subject: WeatherDetailsEntity): Long

    @Query("SELECT * FROM  saved_location")
    fun getAllLocations(): Flowable<List<LocationViewModel>>

}