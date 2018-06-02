package com.codecameo.weather.viewmodels

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected var mCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }
}