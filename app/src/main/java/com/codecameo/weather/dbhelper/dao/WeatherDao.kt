package com.codecameo.weather.dbhelper.dao

import android.arch.persistence.room.*
import com.codecameo.weather.LAST_SEEN_WEATHER_ID
import com.codecameo.weather.dbhelper.DB_WEATHER_DETAIL_TABLE_NAME
import com.codecameo.weather.dbhelper.entity.WeatherDetailsEntity
import com.codecameo.weather.models.WeatherViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntities: WeatherDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createMovieIfNotExists(subject: WeatherDetailsEntity): Long

    @Query("SELECT * FROM  weather_detail  WHERE id = :id LIMIT 1")
    fun getLastSeenWeather(id : Int): Flowable<WeatherViewModel>


    /*@Query("SELECT * FROM "+ DbConstants.MOVIE_TABLE_NAME+" WHERE user_id = :id LIMIT 1")
    LiveData<MovieEntity> getSubject(int id);

    @Query("SELECT * FROM "+ DbConstants.MOVIE_TABLE_NAME+" WHERE user_id = :id LIMIT 1")
    MovieEntity getSubjectEntity(int id);*/

    @Update
    fun updateMovie(weather: WeatherDetailsEntity)
}