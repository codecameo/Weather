package com.codecameo.weather.data

import android.location.Address
import android.location.Geocoder
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.network.interfaces.GoogleApiService
import com.codecameo.weather.network.models.address.AddressApiModel
import com.codecameo.weather.utils.isListEmpty
import com.codecameo.weather.utils.parseLocation
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import io.reactivex.functions.Function
import javax.inject.Inject

class LocationDataProvider @Inject constructor(val geoCoder: Geocoder, val googleApiService: GoogleApiService) : LocationDataManager {
    override fun getLocation(lat: Double, lng: Double): Single<String> {
        return Observable
                .concat<String>(getGeoCoderLocation(lat, lng), getLocationFromAPi(lat, lng).toObservable())
                .subscribeOn(Schedulers.io())
                .first(EMPTY_STRING)
                .observeOn(AndroidSchedulers.mainThread());
    }


    fun getGeoCoderLocation(lat : Double, lng : Double): Observable<String> {
        try {
            val addresses : List<Address>  = geoCoder.getFromLocation(lat, lng, 1);
            if(addresses.size > 0){
                var location = "${addresses.get(0).locality?: addresses.get(0).adminArea}, ${addresses.get(0).countryName}"
                return Observable.just(location)
            }
        }
        catch (e1 : IOException) {
            e1.printStackTrace()
        }
        return Observable.empty()
    }

    override fun getLocationFromAPi(lat : Double, lng : Double): Single<String> {
        return googleApiService.getLocation("$lat,$lng")
            .map(object : Function<AddressApiModel, String>{
                override fun apply(addressApiModel: AddressApiModel): String {
                    if(!isListEmpty(addressApiModel.results)) return parseLocation(addressApiModel.results.get(0))
                    return EMPTY_STRING
                }
            })
    }
}