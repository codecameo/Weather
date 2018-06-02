package com.codecameo.weather.network.interfaces

import com.codecameo.weather.network.ApiConstant
import com.codecameo.weather.network.models.address.AddressApiModel
import com.codecameo.weather.network.models.timezone.TimeZoneApiModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApiService {

    @GET("geocode/json")
    fun getLocation(@Query("latlng") latlng : String, @Query("sensor") sensor : Boolean = true) : Single<AddressApiModel>

    @GET("timezone/json")
    fun getTimeZone(@Query("location") location : String, @Query("timestamp") timestamp : Long, @Query("key") key : String = ApiConstant.APP_KEY_TIMEZONE) : Single<TimeZoneApiModel>
}