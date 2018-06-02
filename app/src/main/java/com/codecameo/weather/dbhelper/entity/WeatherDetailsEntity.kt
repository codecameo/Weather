package com.codecameo.weather.dbhelper.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.codecameo.weather.EMPTY_STRING

import com.codecameo.weather.dbhelper.DB_WEATHER_DETAIL_TABLE_NAME

@Entity(tableName = DB_WEATHER_DETAIL_TABLE_NAME)
data class WeatherDetailsEntity(@PrimaryKey var id : Int){
    var location : String = EMPTY_STRING
    var temp : Int = 0
    var condition : String = EMPTY_STRING
    var icon : String = EMPTY_STRING
    var wind : String = EMPTY_STRING
    var humidity : Int = 0
    var pressure : Double = 0.0
    var sunrise : Long = 0
    var sunset : Long = 0
    var maxTemp : Int = 0
    var minTemp : Int = 0
    var timeZoneId : String = EMPTY_STRING
}
