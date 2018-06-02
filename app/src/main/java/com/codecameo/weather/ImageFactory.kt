package com.codecameo.weather

fun getIconRes(code: String?): Int {
    return when (code) {
        WeatherState.CLEAR_SKY_DAY -> R.drawable.icon_clear_sky_day
        WeatherState.CLEAR_SKY_NIGHT -> R.drawable.icon_clear_sky_night
        WeatherState.FEW_CLOUDS_DAY -> R.drawable.icon_few_clouds_day
        WeatherState.FEW_CLOUDS_NIGHT -> R.drawable.icon_few_clouds_night
        WeatherState.SCATTERED_CLOUDS_DAY -> R.drawable.icon_scattered_clouds_day
        WeatherState.SCATTERED_CLOUDS_NIGHT -> R.drawable.icon_scattered_clouds_night
        WeatherState.BROKEN_CLOUDS_DAY -> R.drawable.icon_broken_clouds_day
        WeatherState.BROKEN_CLOUDS_NIGHT -> R.drawable.icon_broken_clouds_night
        WeatherState.SHOWER_RAIN_DAY -> R.drawable.icon_shower_rain_day
        WeatherState.SHOWER_RAIN_NIGHT -> R.drawable.icon_shower_rain_night
        WeatherState.RAIN_DAY -> R.drawable.icon_rain_day
        WeatherState.RAIN_NIGHT -> R.drawable.icon_rain_night
        WeatherState.THUNDERSTORM_DAY -> R.drawable.icon_thunderstorm_day
        WeatherState.THUNDERSTORM_NIGHT -> R.drawable.icon_thunderstorm_night
        WeatherState.SNOW_DAY -> R.drawable.icon_snow_day
        WeatherState.SNOW_NIGHT -> R.drawable.icon_snow_night
        WeatherState.MIST_DAY -> R.drawable.icon_mist_day
        WeatherState.MIST_NIGHT -> R.drawable.icon_mist_night
        else -> R.drawable.icon_clear_sky_day // shouldn't happen anyways
    }
}

fun getBackgroundsRes(code: String?) = when (code) {
    WeatherState.CLEAR_SKY_DAY -> R.drawable.illustration_clear_sky_day
    WeatherState.CLEAR_SKY_NIGHT -> R.drawable.illustration_clear_sky_night
    WeatherState.FEW_CLOUDS_DAY -> R.drawable.illustration_few_clouds_day
    WeatherState.FEW_CLOUDS_NIGHT -> R.drawable.illustration_few_clouds_night
    WeatherState.SCATTERED_CLOUDS_DAY -> R.drawable.illustration_scattered_clouds_day
    WeatherState.SCATTERED_CLOUDS_NIGHT -> R.drawable.illustration_scattered_clouds_night
    WeatherState.BROKEN_CLOUDS_DAY -> R.drawable.illustration_broken_clouds_day
    WeatherState.BROKEN_CLOUDS_NIGHT -> R.drawable.illustration_broken_clouds_night
    WeatherState.SHOWER_RAIN_DAY -> R.drawable.illustration_shower_rain_day
    WeatherState.SHOWER_RAIN_NIGHT -> R.drawable.illustration_shower_rain_night
    WeatherState.RAIN_DAY -> R.drawable.illustration_rain_day
    WeatherState.RAIN_NIGHT -> R.drawable.illustration_rain_night
    WeatherState.THUNDERSTORM_DAY -> R.drawable.illustration_thunderstorm_day
    WeatherState.THUNDERSTORM_NIGHT -> R.drawable.illustration_thunderstorm_night
    WeatherState.SNOW_DAY -> R.drawable.illustration_snow_day
    WeatherState.SNOW_NIGHT -> R.drawable.illustration_snow_night
    WeatherState.MIST_DAY -> R.drawable.illustration_mist_day
    WeatherState.MIST_NIGHT -> R.drawable.illustration_mist_night
    else -> R.drawable.illustration_clear_sky_day
}