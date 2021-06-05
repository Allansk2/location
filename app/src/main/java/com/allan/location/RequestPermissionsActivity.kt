package com.allan.location

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.contains as contains

class RequestPermissionsActivity : AppCompatActivity() {
    private lateinit var locationProviderClient: LocationProviderClient
    private lateinit var backgroundLocationProvider: BackgroundLocationProvider
    private lateinit var locationTestData: LocationTestData

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
//        locationProviderClient = LocationProviderClient(this)
        backgroundLocationProvider = BackgroundLocationProvider(this)

        locationTestData = LocationTestData(this)
        locationTestData.insertTestData().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("locationTestData", "insert done.")

            },
                { error -> Log.e("locationTestData", "Unable to insert $error") })
//        locationTestData.getAllLocationData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ list ->
//                Log.d("locationTestData", "Total data ${list.size}")
//
//                list?.forEach {
//                    Log.d("locationTestData", it.toString())
//                }
//            },
//                { error -> Log.e("locationTestData", "Unable to get username $error") })

        locationTestData.getLocationData(43.1040, -78.4453)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d("locationTestData", "Total data ${list.size}")

                list?.forEach {
                    Log.d("locationTestData", it.toString())
                }
            },
                { error -> Log.e("locationTestData", "Unable to get username $error") })
    }

    private fun requestPermissions() = Dexter.withContext(this@RequestPermissionsActivity)
        .withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    val deniedPermissionResponses = report.deniedPermissionResponses
                    val grantedPermissionResponses = report.grantedPermissionResponses
//                    locationProviderClient.requestLocaiton()
                    backgroundLocationProvider.requestLocaiton()

//                    if (report.areAllPermissionsGranted()) {
//                        Toast.makeText(this@RequestPermissionsActivity, "All permissions are gratend", Toast.LENGTH_SHORT).show()
//                        locationProviderClient.requestLocaiton()
//                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                // Remember to invoke this method when the custom rationale is closed
                // or just by default if you don't want to use any custom rationale.
                token?.continuePermissionRequest()
            }
        })
        .withErrorListener {
            Toast.makeText(this@RequestPermissionsActivity, it.name, Toast.LENGTH_SHORT).show()
        }
        .check()

}