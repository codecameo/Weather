package com.codecameo.weather.di

import android.arch.lifecycle.ViewModel

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */

@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)