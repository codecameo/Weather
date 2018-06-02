package com.codecameo.weather.network.apiclient

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleApiClient @Inject
constructor() {
    var retrofitDataClient: Retrofit? = null
        private set
    val gson: Gson

    init {
        gson = setGson()
        retrofitDataClient = setDataClient()
    }

    private fun setGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    fun setDataClient(): Retrofit? {
        if (retrofitDataClient == null) {
            retrofitDataClient = Retrofit.Builder()
                    .baseUrl(BASE_DATA_URL + URL_EXTENSION)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return this!!.retrofitDataClient
    }

    fun getRetrofitClient(): Retrofit {
        return this.retrofitDataClient!!
    }

    companion object {
        val BASE_DATA_URL = "https://maps.googleapis.com/maps/"
        private val URL_EXTENSION = "api/"
    }
}