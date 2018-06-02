package com.codecameo.weather.network.interfaces

import com.codecameo.weather.network.ApiConstant
import com.codecameo.weather.network.models.weather.WeatherDetailsApiModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    fun getCurrentWeather(@Query("lat") lat : Double, @Query("lon") lng : Double, @Query("appid") appId : String) : Single<WeatherDetailsApiModel>
}