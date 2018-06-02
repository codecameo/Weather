package com.codecameo.weather.network.models.address

class AddressApiModel {
    lateinit var results : List<AddressComponent>
    data class AddressComponent(val address_components : List<Address>)
    data class Address(val long_name:String, val short_name: String, val types : List<String>)
}