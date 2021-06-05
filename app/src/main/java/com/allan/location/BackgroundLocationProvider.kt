package com.allan.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Criteria.ACCURACY_FINE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log


/**
 *
 * Created by Allan Zhu on 2021-06-05.
 */
class BackgroundLocationProvider(private val context: Context) : LocationListener {
    private val TAG = "BackgroundLocationProvider"

    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager;

    @SuppressLint("MissingPermission")
    fun requestLocaiton() {
        val criteria: Criteria = Criteria()
        criteria.accuracy = ACCURACY_FINE
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isCostAllowed = true
        criteria.powerRequirement = Criteria.POWER_HIGH

        var provider = locationManager.getBestProvider(criteria, true)
        val providerList = locationManager.getProviders(true)
        provider = when {
            providerList.contains(LocationManager.NETWORK_PROVIDER) -> {
                LocationManager.NETWORK_PROVIDER
            }
            providerList.contains(LocationManager.GPS_PROVIDER) -> {
                LocationManager.GPS_PROVIDER
            }
            else -> {
                Log.e(TAG, "Please Open Your GPS or Location Service")
                // TODO: 2021-06-05
                // Need to direct user to enable location service
                return
            }
        }

        val location = locationManager.getLastKnownLocation(provider)
        location?.let {
            val latitude = location.latitude
            val longitude = location.longitude
            val accuracy = location.accuracy
            Log.d(TAG, "getLastKnownLocation latitude: $latitude, longitude: $longitude, accuracy: $accuracy")
        }

        val minTimeMs: Long = 20000 // 20 seconds
        val minDistanceM = 100.0F // 100 meters
        locationManager.requestLocationUpdates(provider, minTimeMs, minDistanceM, this);
    }

    override fun onLocationChanged(location: Location) {
        location?.let {
            val latitude = location.latitude
            val longitude = location.longitude
            val accuracy = location.accuracy
            Log.d(TAG, "onLocationChanged latitude: $latitude, longitude: $longitude, accuracy: $accuracy")
        }
    }
}