package com.codecameo.weather.activities

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity(){

    protected lateinit var mBinding: DB
    protected lateinit var mViewModel: VM
    protected var mCompositeDisposable = CompositeDisposable()
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    protected fun provideViewModel(fragment: FragmentActivity, modelClass: Class<VM>): VM {
        return ViewModelProviders.of(fragment, viewModelFactory).get(modelClass)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.dispose()
    }

    init {
        val TAG : String = this.javaClass.name
    }
}