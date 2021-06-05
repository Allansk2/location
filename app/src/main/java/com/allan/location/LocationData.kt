package com.allan.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.allan.location.LocationData.Companion.TABLE
import java.util.*

/**
 *
 * Created by Allan Zhu on 2021-06-05.
 */
@Entity(tableName = TABLE)
data class LocationData(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val start_latitude: Double,
    val start_longitude: Double,
    val start_timestamp: Long,
    val start_fuel: Int,
    val end_latitude: Double,
    val end_longitude: Double,
    val travel_duration: Long,
    val fuel_used: Int
) {
    companion object {
        const val TABLE = "location_data"
    }
}