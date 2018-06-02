package com.codecameo.weather.utils

import android.Manifest
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.content.Intent
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.support.design.widget.Snackbar
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.app.Activity
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.WRITE_CONTACTS
import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.codecameo.weather.R


object PermissionHandler {

    private val REQUEST_SETTINGS = 444

    // permission request code for location
    val REQUEST_BOTH_LOCATION_PERMISSION = 331
    val REQUEST_COARSE_LOCATION = 332
    val REQUEST_FINE_LOCATION = 333

    fun checkPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (!checkPermission(context, permission)) {
                return false
            }
        }
        return true
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }


    //=== request permission set for location ===//
    fun requestPermissions(o: Any, permissionId: Int, message: String, vararg permissions: String) {
        if (o is Activity) {

            var showSnackBar = true
            for (permission in permissions) {
                showSnackBar = showSnackBar and ActivityCompat.shouldShowRequestPermissionRationale(o as AppCompatActivity, permission)
            }
            if (showSnackBar) {
                showSettingsSnackbar(message, o)
            } else {
                ActivityCompat.requestPermissions(o as AppCompatActivity, permissions, permissionId)
            }
        }
    }

    fun requestPermissions(o: Any, permissionId: Int, message: String, permission: String) {
        if (o is Activity) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(o as AppCompatActivity, permission)) {
                showSettingsSnackbar(message, o as Activity)
            } else {
                ActivityCompat.requestPermissions(o, arrayOf(permission), permissionId)
            }
        }
    }

    fun hasCameraPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.CAMERA)
    }

    fun hasExternalStorageWritePermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun hasExternalStorageReadPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun hasRecordAudioPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.RECORD_AUDIO)
    }

    fun hasContactReadPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.READ_CONTACTS)
    }

    fun hasContactWritePermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.WRITE_CONTACTS)
    }

    fun hasCoarseLocationPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun hasFineLocationPermission(context: Context): Boolean {
        return checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun showSettingsSnackbar(message: String, activity: Activity?) {
        if (activity != null) {
            val view = activity.findViewById<View>(android.R.id.content)
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(activity.getString(R.string.settings), object : View.OnClickListener {
                        override fun onClick(v: View) {
                            val settingsIntent = Intent()
                            settingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            settingsIntent.addCategory(Intent.CATEGORY_DEFAULT)
                            settingsIntent.data = Uri.parse("package:" + activity.packageName)
                            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            activity.startActivityForResult(settingsIntent, REQUEST_SETTINGS)
                        }
                    })
                    .setActionTextColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                    .show()
        }
    }
}