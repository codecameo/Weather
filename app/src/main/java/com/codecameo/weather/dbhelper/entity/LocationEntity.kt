package com.codecameo.weather.dbhelper.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.codecameo.weather.dbhelper.DB_SAVED_LOCATION_TABLE_NAME

@Entity(tableName = DB_SAVED_LOCATION_TABLE_NAME)
data class LocationEntity(@PrimaryKey(autoGenerate = true) val id : Int, val lat : Double, val lng : Double, val location : String)