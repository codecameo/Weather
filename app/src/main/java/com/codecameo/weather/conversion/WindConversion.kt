package com.codecameo.weather.conversion

import com.codecameo.weather.NOT_AVAILABLE
import com.codecameo.weather.WIND_SPEED_UNIT

fun getWindInfo(speed : Double?, deg : Double) : String{
    speed?:return NOT_AVAILABLE
    val direction  = when (deg) {
        null -> ""
        in 22.5..67.5 -> "NE"
        in 67.5..112.5 -> "E"
        in 112.5..157.5 -> "SE"
        in 157.5..202.5 -> "S"
        in 202.5..247.5 -> "SW"
        in 247.5..292.5 -> "W"
        in 292.5..337.5 -> "NW"
        else -> "N"
    }
    return "$speed $WIND_SPEED_UNIT $direction"
}