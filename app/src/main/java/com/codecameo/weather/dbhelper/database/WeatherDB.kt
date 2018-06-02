package com.codecameo.weather.dbhelper.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.codecameo.weather.dbhelper.DB_VERSION
import com.codecameo.weather.dbhelper.dao.LocationDao
import com.codecameo.weather.dbhelper.dao.WeatherDao
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.LocationViewModel
import javax.inject.Singleton

@Singleton
@Database(entities = arrayOf(WeatherDetailsEntity::class,
        LocationEntity::class),
        version = DB_VERSION, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {
    abstract fun getWeatherDetailsDao() : WeatherDao
    abstract fun getLocationListDao() : LocationDao
}