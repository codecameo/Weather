package com.codecameo.weather.diffutils

import android.support.v7.util.DiffUtil

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */
abstract class BaseDiff<Model>(protected var mOldList: List<Model>, protected var mNewList: List<Model>) : DiffUtil.Callback() {

    fun setNewList(newList: List<Model>) {
        this.mNewList = newList
    }

    fun setOldList(oldList: List<Model>) {
        this.mOldList = oldList
    }

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }
}
