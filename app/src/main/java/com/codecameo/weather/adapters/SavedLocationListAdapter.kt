package com.codecameo.weather.adapters

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecameo.weather.R
import com.codecameo.weather.databinding.ItemLocationBinding
import com.codecameo.weather.diffutils.LocationListDiff
import com.codecameo.weather.models.LocationViewModel

class SavedLocationListAdapter : BaseRecyclerAdapter<SavedLocationListAdapter.ViewHolder>() {

    val mSavedLocationList = ArrayList<LocationViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLocationBinding>(LayoutInflater.from(parent.context), R.layout.item_location, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mSavedLocationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mSavedLocationList[position])
    }

    class ViewHolder(val itemBinding: ItemLocationBinding?) : RecyclerView.ViewHolder(itemBinding?.root) {
        fun bindTo(locationViewModel: LocationViewModel) {
            itemBinding?.tvSavedLocation?.text = if(locationViewModel.location.isEmpty()) locationViewModel.location else itemBinding?.root?.context?.getString(R.string.text_not_found)
        }
    }

    /*fun addCurrentLocation(locationViewModel: LocationViewModel){
        mTempLocationList.add(locationViewModel)
        val result = DiffUtil.calculateDiff(LocationListDiff(mSavedLocationList, mTempLocationList))
        mSavedLocationList.clear()
        mSavedLocationList.addAll(mTempLocationList)
        result.dispatchUpdatesTo(listUpdateCallback)
        //notifyDataSetChanged()
    }*/

    fun setData(locationList: List<LocationViewModel>) {
        if (mSavedLocationList.size >0) return
        val result = DiffUtil.calculateDiff(LocationListDiff(mSavedLocationList, locationList))
        mSavedLocationList.clear()
        mSavedLocationList.addAll(locationList)
        result.dispatchUpdatesTo(listUpdateCallback)
        //notifyDataSetChanged()
    }
}