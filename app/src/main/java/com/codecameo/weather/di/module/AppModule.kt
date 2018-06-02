package com.codecameo.weather.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.location.Geocoder
import android.location.LocationProvider
import com.codecameo.weather.data.*
import com.codecameo.weather.dbhelper.DB_NAME
import com.codecameo.weather.dbhelper.database.WeatherDB
import com.codecameo.weather.di.ApplicationContext
import com.codecameo.weather.di.DatabaseInfo
import com.codecameo.weather.network.apiclient.GoogleApiClient
import com.codecameo.weather.network.apiclient.WeatherApiClient
import com.codecameo.weather.network.interfaces.GoogleApiService
import com.codecameo.weather.network.interfaces.WeatherApiService
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {
    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @DatabaseInfo
    internal fun provideDatabaseName(): String {
        return DB_NAME
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(@DatabaseInfo dbName: String, @ApplicationContext context: Context): WeatherDB {
        return Room.databaseBuilder(context, WeatherDB::class.java, dbName).fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    internal fun provideDbManager(dbProvider: DbProvider): DbManager {
        return dbProvider
    }

    @Provides
    @Singleton
    internal fun provideNetworkManager(networkDataProvider: NetworkDataProvider): NetworkDataManager {
        return networkDataProvider
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataProvider): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideLocationManager(locationDataProvider: LocationDataProvider) : LocationDataManager{
        return locationDataProvider
    }

    @Provides
    @Singleton
    internal fun weatherApiServiceProvider(weatherApiClient: WeatherApiClient): WeatherApiService {
        return weatherApiClient.getRetrofitClient().create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun googleApiServiceProvider(googleApiClient: GoogleApiClient): GoogleApiService {
        return googleApiClient.getRetrofitClient().create(GoogleApiService::class.java)
    }

    @Provides
    internal fun getGeoCoder(@ApplicationContext context: Context) : Geocoder{
        return Geocoder(context, Locale.getDefault())
    }

    /*@Provides
    @PreferenceInfo
    internal fun provideSharedPrefName(): String {
        return SharedPreference.MODULE_NAME
    }

    @Provides
    @PreferenceInfo
    internal fun provideSharedPrefVersion(): Int {
        return SharedPreference.VERSION
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataProvider): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbProvider: DbProvider): DbHelper {
        return appDbProvider
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesProvider: PreferenceProvider): PreferenceHelper {
        return appPreferencesProvider
    }

    @Provides
    @Singleton
    internal fun apiServiceProvider(apiClient: ApiClient): MovieApiService {
        return apiClient.getRetrofitDataClient().create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideSharedPref(@ApplicationContext context: Context,
                                   @PreferenceInfo moduleName: String,
                                   @PreferenceInfo version: Int): SharedPreference {
        return SharedPreference(context, moduleName, version)
    }*/
}
