package com.codecameo.weather.models

import android.arch.persistence.room.ColumnInfo
import com.codecameo.weather.DEFAULT_DOUBLE
import com.codecameo.weather.EMPTY_STRING


data class WeatherViewModel(val id : Int){
    lateinit var location : String
    var temp : Int = 0
    lateinit var condition : String
    lateinit var icon : String
    lateinit var wind : String
    var humidity : Int = 0
    var pressure : Double = DEFAULT_DOUBLE
    var sunrise : Long = 0
    var sunset : Long = 0
    var maxTemp : Int = 0
    var minTemp : Int = 0
    lateinit var timeZoneId : String
}