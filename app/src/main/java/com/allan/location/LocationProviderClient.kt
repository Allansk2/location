package com.allan.location

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.util.Log
import com.google.android.gms.location.*


/**
 *
 * Created by Allan Zhu on 2021-06-05.
 */
class LocationProviderClient(private val activity: Activity) {
    private val TAG = "LocationProviderClient"
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    fun requestLocaiton() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val accuracy = location.accuracy
                    Log.d(TAG, "latitude: $latitude, longitude: $longitude, accuracy: $accuracy")
                }
            }

        fusedLocationClient.lastLocation
            .addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {

                    val location = taskLocation.result
                    location?.let {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val accuracy = location.accuracy
                        Log.d(TAG, "getLastLocation latitude: $latitude, longitude: $longitude, accuracy: $accuracy")
                    }
                } else {
                    Log.w(TAG, "getLastLocation:exception", taskLocation.exception)
                }
            }

        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val accuracy = location.accuracy
                        Log.d(TAG, "latitude: $latitude, longitude: $longitude, accuracy: $accuracy")
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}