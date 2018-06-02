package com.codecameo.weather.repositories

import com.codecameo.weather.data.DataManager
import com.codecameo.weather.network.interfaces.WeatherApiService

open class BaseRepo(protected val dataManager : DataManager)