package com.codecameo.weather.diffutils

import android.util.Log
import com.codecameo.weather.models.LocationViewModel

/**
 * Created by Md. Sifat-Ul Haque on 12/25/2017.
 */
class LocationListDiff(oldLocationList: List<LocationViewModel>, newLocationList: List<LocationViewModel>) : BaseDiff<LocationViewModel>(oldLocationList, newLocationList) {

    val TAG = "LocationListDiff"

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d(TAG,"${mOldList[oldItemPosition].id} ${mNewList[newItemPosition].id}");
        return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].lat == mNewList[newItemPosition].lat && mOldList[oldItemPosition].lng == mNewList[newItemPosition].lng
    }
}
