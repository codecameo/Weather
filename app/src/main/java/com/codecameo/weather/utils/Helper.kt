package com.codecameo.weather.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.text.TextUtils
import android.widget.Toast
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.network.models.address.AddressApiModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

const val AREA_LEVEL_2 = "administrative_area_level_2";
const val AREA_LEVEL_1 = "administrative_area_level_1";
const val AREA_LEVEL_COUNTRY = "country";

fun isListEmpty(list : List<Any>?) : Boolean{
    list?.let { return it.size == 0 }
    return true
}

fun parseLocation(component: AddressApiModel.AddressComponent) : String {
    var location : String = EMPTY_STRING
    for (address : AddressApiModel.Address in component.address_components){
        if (address.types[0] == AREA_LEVEL_2 || address.types[0] == AREA_LEVEL_COUNTRY || address.types[0] == AREA_LEVEL_1)
            location+=", ${address.long_name}"
    }
    return location.let { if (TextUtils.isEmpty(it)) location else location.substring(2, location.length)}
}

fun showToast(context: Context, message : String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}