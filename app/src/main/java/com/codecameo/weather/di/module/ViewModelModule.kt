package com.codecameo.weather.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.codecameo.weather.di.ViewModelKey
import com.codecameo.weather.viewmodels.LocationPickerViewModel
import com.codecameo.weather.viewmodels.WeatherDetailViewModel
import com.codecameo.weather.viewmodels.factory.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDetailViewModel::class)
    internal abstract fun bindsWeatherDetailsViewModel(weatherDetailViewModel: WeatherDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationPickerViewModel::class)
    internal abstract fun bindsLocationPickerViewModel(locationPickerViewModel: LocationPickerViewModel): ViewModel

    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
