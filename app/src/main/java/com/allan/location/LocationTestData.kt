package com.allan.location

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *
 * Created by Allan Zhu on 2021-06-05.
 */
class LocationTestData(context: Context) {
    private val locationDao: LocationDao = LocationDatabase.getInstance(context.applicationContext).locationDao()

    // From H to W
    // H 43.1040, -78.4453
    // W 42.8332, 78.3266
    val location1 = LocationData(
        uid = "1",
        start_latitude = 43.1039646,
        start_longitude = -78.4452845,
        start_timestamp = 1622923883,
        start_fuel = 569,
        end_latitude = 42.833180,
        end_longitude = -78.326590,
        travel_duration = 1680,
        fuel_used = 25
    )

    val location2 = LocationData(
        uid = "2",
        start_latitude = 43.1036646,
        start_longitude = -78.4453845,
        start_timestamp = 1622923224,
        start_fuel = 531,
        end_latitude = 42.833380,
        end_longitude = -78.326690,
        travel_duration = 1698,
        fuel_used = 26
    )

    val location3 = LocationData(
        uid = "3",
        start_latitude = 43.1037646,
        start_longitude = -78.4450845,
        start_timestamp = 1622922924,
        start_fuel = 491,
        end_latitude = 42.832980,
        end_longitude = -78.326490,
        travel_duration = 1710,
        fuel_used = 24
    )

    // From H to W, but H does not match radius
    // H 43.1040, -78.4453
    // W 42.8332, 78.3266
    // latitude doesn't match
    val location4 = LocationData(
        uid = "4",
        start_latitude = 43.1034646,
        start_longitude = -78.4452845,
        start_timestamp = 1622923883,
        start_fuel = 569,
        end_latitude = 42.833180,
        end_longitude = -78.326590,
        travel_duration = 1680,
        fuel_used = 25
    )

    // longitude doesn't match
    val location5 = LocationData(
        uid = "5",
        start_latitude = 43.1036646,
        start_longitude = -78.4448845,
        start_timestamp = 1622923224,
        start_fuel = 531,
        end_latitude = 42.833380,
        end_longitude = -78.326690,
        travel_duration = 1698,
        fuel_used = 26
    )

    // From H to O
    // H 43.1040, -78.4453
    // same longitude, different latitude
    val location6 = LocationData(
        uid = "6",
        start_latitude = 43.1039646,
        start_longitude = -78.4452845,
        start_timestamp = 1622283883,
        start_fuel = 428,
        end_latitude = 43.082253,
        end_longitude = -78.4452845,
        travel_duration = 980,
        fuel_used = 8
    )

    // same latitude, different longitude
    val location7 = LocationData(
        uid = "7",
        start_latitude = 43.1039646,
        start_longitude = -78.4452845,
        start_timestamp = 1622923883,
        start_fuel = 398,
        end_latitude = 43.1039646,
        end_longitude = -78.496590,
        travel_duration = 1078,
        fuel_used = 13
    )

    // different latitude, different longitude
    val location8 = LocationData(
        uid = "8",
        start_latitude = 43.1039646,
        start_longitude = -78.4452845,
        start_timestamp = 1622923883,
        start_fuel = 368,
        end_latitude = 43.2839646,
        end_longitude = -78.496590,
        travel_duration = 889,
        fuel_used = 15
    )


    // From W to H
    // H 43.1040, -78.4453
    // W 42.8332, 78.3266
    val location9 = LocationData(
        uid = "9",
        start_latitude = 42.833180,
        start_longitude = -78.326590,
        start_timestamp = 1622923883,
        start_fuel = 654,
        end_latitude = 43.1039646,
        end_longitude = -78.4452845,
        travel_duration = 1877,
        fuel_used = 25
    )

    val location10 = LocationData(
        uid = "10",
        start_latitude = 42.833380,
        start_longitude = -78.326690,
        start_timestamp = 1622923224,
        start_fuel = 531,
        end_latitude = 43.1036646,
        end_longitude = -78.4453845,
        travel_duration = 1698,
        fuel_used = 26
    )

    val location11 = LocationData(
        uid = "11",
        start_latitude = 42.832980,
        start_longitude = -78.326490,
        start_timestamp = 1622922924,
        start_fuel = 491,
        end_latitude = 43.1037646,
        end_longitude = -78.4450845,
        travel_duration = 1710,
        fuel_used = 24
    )

    // From W to O
    // W 42.8332, -78.3266
    // same longitude, different latitude
    val location12 = LocationData(
        uid = "12",
        start_latitude = 42.8332646,
        start_longitude = -78.3266845,
        start_timestamp = 1622283883,
        start_fuel = 428,
        end_latitude = 43.082253,
        end_longitude = 78.3266845,
        travel_duration = 980,
        fuel_used = 8
    )

    // same latitude, different longitude
    val location13 = LocationData(
        uid = "13",
        start_latitude = 42.8332646,
        start_longitude = -78.3266845,
        start_timestamp = 1622283883,
        start_fuel = 428,
        end_latitude = 442.833253,
        end_longitude = -78.316145,
        travel_duration = 980,
        fuel_used = 8
    )

    val locations = listOf(
        location1,
        location2,
        location3,
        location4,
        location5,
        location6,
        location7,
        location8,
        location9,
        location10,
        location11,
        location12,
        location13
    )

    fun insertTestData(): Completable {
        return locationDao.insertAllLocations(locations)
    }

    fun getAllLocationData(): Flowable<List<LocationData>> {
        return locationDao.getAllLocationData()
    }

    fun getLocationData(latitude: Double, longitude: Double): Flowable<List<LocationData>> {
        return getLocationData(latitude - 0.0004, latitude + 0.0004, longitude - 0.0004, longitude + 0.0004)
    }

    fun getLocationData(latitude_min: Double, latitude_max: Double, longitude_min: Double, longitude_max: Double): Flowable<List<LocationData>> {
        return locationDao.getAllLocationData(latitude_min, latitude_max, longitude_min, longitude_max)
    }
}