package com.codecameo.weather.network.models.timezone

data class TimeZoneApiModel(val dstOffset : Long, val rawOffset : Long, val timeZoneId : String, val timeZoneName : String)