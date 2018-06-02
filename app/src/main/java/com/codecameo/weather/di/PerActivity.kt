package com.codecameo.weather.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class PerActivity

