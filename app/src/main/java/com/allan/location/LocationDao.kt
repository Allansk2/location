package com.allan.location

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 *
 * Created by Allan Zhu on 2021-06-05.
 */
@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationData: LocationData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLocations(locationData: List<LocationData>): Completable

    @Query("DELETE FROM ${LocationData.TABLE}")
    fun deleteAllLocations()

    @Query("SELECT * FROM ${LocationData.TABLE} WHERE uid = :id")
    fun getLocationById(id: String): Flowable<LocationData>

    @Query("SELECT * FROM ${LocationData.TABLE} ORDER BY uid ASC")
    fun getAllLocationData(): Flowable<List<LocationData>>

    @Query("SELECT * FROM ${LocationData.TABLE} WHERE start_latitude >= :latitude_min AND start_latitude <= :latitude_max AND start_longitude >= :longitude_min AND start_longitude <= :longitude_max ORDER BY uid ASC")
    fun getAllLocationData(latitude_min: Double, latitude_max: Double, longitude_min: Double, longitude_max: Double): Flowable<List<LocationData>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocationData(locationData: LocationData)
}