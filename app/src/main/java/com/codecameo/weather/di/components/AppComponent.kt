package com.codecameo.weather.di.components

import android.app.Application

import com.codecameo.weather.WeatherApplication
import com.codecameo.weather.di.builder.ActivityBuilderModule
import com.codecameo.weather.di.module.AppModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, AndroidInjectionModule::class, ActivityBuilderModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApplication)
}
