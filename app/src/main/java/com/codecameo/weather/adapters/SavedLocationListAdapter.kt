package com.codecameo.weather.adapters

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecameo.weather.R
import com.codecameo.weather.databinding.ItemLocationBinding
import com.codecameo.weather.diffutils.LocationListDiff
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.utils.isListEmpty

class SavedLocationListAdapter : BaseRecyclerAdapter<SavedLocationListAdapter.ViewHolder>() {

    val mSavedLocationList = ArrayList<LocationViewModel>()
    lateinit var mOnItemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLocationBinding>(LayoutInflater.from(parent.context), R.layout.item_location, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mSavedLocationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, mSavedLocationList[position].location)
        holder.bindTo(mSavedLocationList[position])
    }

    inner class ViewHolder(val itemBinding: ItemLocationBinding?) : RecyclerView.ViewHolder(itemBinding?.root), View.OnClickListener {
        override fun onClick(v: View?) {
            mOnItemClickListener.OnItemClicked(mSavedLocationList[adapterPosition])
        }

        init {
            itemBinding?.root?.setOnClickListener(this)
        }
        fun bindTo(locationViewModel: LocationViewModel) {
            itemBinding?.location = if(!locationViewModel?.location.isEmpty()) locationViewModel.location else itemBinding?.root?.context?.getString(R.string.text_not_found)
        }
    }

    fun setData(locationList: List<LocationViewModel>?) {
        if (isListEmpty(locationList)) return
        val result = DiffUtil.calculateDiff(LocationListDiff(mSavedLocationList, locationList!!))
        mSavedLocationList.clear()
        mSavedLocationList.addAll(locationList)
        result.dispatchUpdatesTo(listUpdateCallback)
    }

    companion object {
        const val TAG = "SavedLocationListAd"
    }

    interface OnItemClickListener{
        fun OnItemClicked(locationViewModel: LocationViewModel)
    }
}