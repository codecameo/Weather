package com.codecameo.weather.di.builder

import com.codecameo.weather.activities.LocationPickerActivity
import com.codecameo.weather.activities.WeatherDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun bindWeatherDetailsActivity(): WeatherDetailsActivity

    @ContributesAndroidInjector
    internal abstract fun bindLocationPickerActivity(): LocationPickerActivity
}