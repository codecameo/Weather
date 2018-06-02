package com.codecameo.weather.data

import com.codecameo.weather.network.models.address.AddressApiModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface LocationDataManager {
    fun getLocation(lat : Double, lng : Double) : Single<String>
    fun getLocationFromAPi(lat : Double, lng : Double) : Single<String>
}