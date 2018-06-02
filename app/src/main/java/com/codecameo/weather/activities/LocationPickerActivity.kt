package com.codecameo.weather.activities

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.codecameo.weather.DEFAULT_DOUBLE
import com.codecameo.weather.EMPTY_STRING
import com.codecameo.weather.R
import com.codecameo.weather.databinding.ActivityLocationPickerBinding
import com.codecameo.weather.dbhelper.entity.LocationEntity
import com.codecameo.weather.utils.showToast
import com.codecameo.weather.viewmodels.LocationPickerViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import io.reactivex.observers.DisposableSingleObserver
import java.util.*

class LocationPickerActivity : BaseActivity<ActivityLocationPickerBinding, LocationPickerViewModel>(), OnMapReadyCallback, View.OnClickListener {
    override fun onClick(v: View?) {
        val id = v?.id
        when(id){
            R.id.tv_picker_location -> pickCurrentLocation()
        }
    }

    private fun pickCurrentLocation() {
        mViewModel.saveLocation(LocationEntity(0, mViewModel.mLat, mViewModel.mLng, mViewModel.mLocation))
        val intent = Intent()
        intent.putExtra(KEY_LAT, mViewModel.mLat)
        intent.putExtra(KEY_LNG, mViewModel.mLng)
        intent.putExtra(KEY_LOCATION, mViewModel.mLocation)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_location_picker
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mCurrentLocation : LatLng
    private lateinit var mSelectedLocation : LatLng
    private lateinit var mGeoCoder: Geocoder
    private lateinit var disposableSingleObserver : DisposableSingleObserver<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = provideViewModel(this, LocationPickerViewModel::class.java)
        extractData()
        initVariable()
        bindData()
        initListeners()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun bindData() {
        mBinding.setLocation(mViewModel.mLocation)
    }

    private fun initListeners() {
        mBinding.tvPickerLocation.setOnClickListener(this)
    }

    private fun initVariable() {
        mCurrentLocation = LatLng(mViewModel.mLat, mViewModel.mLng)
        mGeoCoder = Geocoder(this, Locale.getDefault())
    }

    private fun extractData() {
        mViewModel.mLat = intent.getDoubleExtra(KEY_LAT, DEFAULT_DOUBLE)
        mViewModel.mLng = intent.getDoubleExtra(KEY_LNG, DEFAULT_DOUBLE)
        mViewModel.mLocation = intent.getStringExtra(KEY_LOCATION)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 16f)
        googleMap.animateCamera(cameraUpdate)
        mMap.setOnCameraIdleListener(object : GoogleMap.OnCameraIdleListener{
            override fun onCameraIdle() {
                mSelectedLocation = if (!::mSelectedLocation.isInitialized) mCurrentLocation else mMap.cameraPosition.target.also {
                    mViewModel.mLat = it.latitude
                    mViewModel.mLng = it.longitude
                    fetchLocation()
                }
            }
        })
    }

    private fun fetchLocation() {
        if (::disposableSingleObserver.isInitialized) disposableSingleObserver.dispose()
        disposableSingleObserver = mViewModel.getLocation().subscribeWith(object : DisposableSingleObserver<String>(){
            override fun onSuccess(location: String) {
                mViewModel.mLocation = location
                mBinding.location = mViewModel.mLocation
            }

            override fun onError(e: Throwable) {
                showToast(this@LocationPickerActivity, getString(R.string.text_error_message))
            }

        })
    }

    companion object {
        const val KEY_LAT = "key_lat"
        const val KEY_LNG = "key_lng"
        const val KEY_LOCATION = "key_location"
    }
}
