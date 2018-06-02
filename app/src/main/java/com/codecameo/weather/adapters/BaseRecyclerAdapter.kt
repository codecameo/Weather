package com.codecameo.weather.adapters

import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.RecyclerView

/**
 * Created by Oshan on 4/5/18.
 */

abstract class BaseRecyclerAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    protected var listUpdateCallback: ListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any) {
            notifyItemRangeChanged(position, count)
        }
    }
}
