package com.codecameo.weather.activities

import android.Manifest
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.codecameo.weather.R
import com.codecameo.weather.databinding.ActivityWeatherDetailsBinding
import com.codecameo.weather.viewmodels.WeatherDetailViewModel
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.app_bar_weather_details.*
import kotlinx.android.synthetic.main.content_weather_details.*
import com.codecameo.weather.models.WeatherViewModel
import io.reactivex.functions.Consumer
import com.codecameo.weather.utils.PermissionHandler
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Handler
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.codecameo.weather.DEFAULT_DOUBLE
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.LAST_SEEN_WEATHER_ID
import com.codecameo.weather.adapters.SavedLocationListAdapter
import com.codecameo.weather.databinding.adapters.setImageBackgroundSrc
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.models.LocationViewModel
import com.codecameo.weather.utils.PermissionHandler.REQUEST_COARSE_LOCATION
import com.codecameo.weather.utils.showToast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.android.synthetic.main.nav_header_weather_details.*
import java.util.*


class WeatherDetailsActivity : BaseActivity<ActivityWeatherDetailsBinding, WeatherDetailViewModel>(), NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private val REQUEST_CODE_LOCATION_PICKER = 4003

    private lateinit var mGoogleApiClient : GoogleApiClient
    private lateinit var mHandler: Handler
    private lateinit var mGeoCoder: Geocoder
    private lateinit var mLocationListAdapter : SavedLocationListAdapter
    private val mUpdateWeatherInfo : Runnable = object : Runnable{
        override fun run() {
            if (ActivityCompat.checkSelfPermission(this@WeatherDetailsActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (mGoogleApiClient.isConnected()) {
                    var lastLocation : android.location.Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                    if (lastLocation != null) {
                        disconnectGoogleApiClient()
                        mViewModel.mLat = lastLocation.latitude
                        mViewModel.mLng = lastLocation.longitude
                        updateData()
                    }
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_weather_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = provideViewModel(this, WeatherDetailViewModel::class.java)
        setSupportActionBar(toolbar)
        setNavBar()
        initVariable()
        initListeners()
        setupView()
        setupList()
        enableLocation()
        setLastSeenWeatherData()
        setLocationListData()
    }

    private fun setupList() {
        mBinding.rvLocationList.layoutManager = LinearLayoutManager(this)
        mBinding.rvLocationList.adapter = mLocationListAdapter
    }


    private fun initListeners() {
        mBinding.appBarInclude?.fabAddLocation?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (mViewModel.isLocationSet()){
                    val intent = Intent(this@WeatherDetailsActivity, LocationPickerActivity::class.java)
                    intent.putExtra(LocationPickerActivity.KEY_LAT, mViewModel.mLat)
                    intent.putExtra(LocationPickerActivity.KEY_LNG, mViewModel.mLng)
                    intent.putExtra(LocationPickerActivity.KEY_LOCATION, mViewModel.mLocation)
                    startActivityForResult(intent, REQUEST_CODE_LOCATION_PICKER)
                }else{
                    enableLocation()
                }
            }
        })
    }

    private fun setNavBar() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }


    private fun initVariable() {
        mHandler = Handler()
        mLocationListAdapter = SavedLocationListAdapter()
        mGeoCoder = Geocoder(this, Locale.getDefault());
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    private fun connectGoogleApiClient() {
        if (!mGoogleApiClient.isConnected && !mGoogleApiClient.isConnecting) {
            mGoogleApiClient.connect()
        }
    }

    private fun disconnectGoogleApiClient() {
        if (mGoogleApiClient.isConnected || mGoogleApiClient.isConnecting) {
            mGoogleApiClient.disconnect()
        }
    }

    private fun updateData() {
        mViewModel.updateWeatherDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver(){
                    override fun onComplete() {}
                    override fun onError(e: Throwable) {
                        showToast(this@WeatherDetailsActivity, getString(R.string.failed_to_update))
                    }
                })
    }

    private fun setLastSeenWeatherData() {
        mCompositeDisposable.add(mViewModel.getLastSeenWeatherDetails().subscribe(object : Consumer<WeatherViewModel>{
            override fun accept(weatherViewModel: WeatherViewModel) {
                mBinding.model = weatherViewModel
                mViewModel.mLocation = weatherViewModel.location
                iv_nav_bar_image?.setImageBackgroundSrc(weatherViewModel.icon)
                tv_time.timeZone = if (weatherViewModel.timeZoneId.isNotEmpty()) weatherViewModel.timeZoneId else return
            }
        },
        object : Consumer<Throwable>{
            override fun accept(t: Throwable) {
                showToast(this@WeatherDetailsActivity, getString(R.string.text_error_message))
            }
        }))
    }

    private fun setLocationListData() {
        mCompositeDisposable.add(mViewModel.getSavedLocationList().subscribe(object : Consumer<List<LocationViewModel>>{
            override fun accept(locationList: List<LocationViewModel>) {
                mLocationListAdapter.setData(locationList)
            }
        }))
    }

    private fun setupView() {
        supportActionBar!!.title = EMPTY_STRING
        tv_time.format24Hour = null
        tv_time.format12Hour = "hh:mma"
    }

    private fun enableLocation() {
        if (!PermissionHandler.hasCoarseLocationPermission(this)) {
            PermissionHandler.requestPermissions(this, PermissionHandler.REQUEST_COARSE_LOCATION, getString(R.string.permission_msg_location), Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            connectGoogleApiClient()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_LOCATION_PICKER -> {
                    data?.let {
                        mViewModel.mLat = it.getDoubleExtra(LocationPickerActivity.KEY_LAT, DEFAULT_DOUBLE)
                        mViewModel.mLng = it.getDoubleExtra(LocationPickerActivity.KEY_LNG, DEFAULT_DOUBLE)
                        mViewModel.mLocation = it.getStringExtra(LocationPickerActivity.KEY_LOCATION)
                        updateData()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_COARSE_LOCATION->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    connectGoogleApiClient()
                } else {
                    Toast.makeText(this, getString(R.string.toast_location_permision_denied), Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onConnected(bundle: Bundle?) {
        mHandler.post(mUpdateWeatherInfo)
    }

    override fun onConnectionSuspended(code: Int) {

    }

    override fun onConnectionFailed(result: ConnectionResult) {
        connectGoogleApiClient()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnectGoogleApiClient()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
