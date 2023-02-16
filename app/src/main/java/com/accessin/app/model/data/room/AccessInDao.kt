package com.accessin.app.model.data.room

import androidx.room.*
import com.accessin.app.model.data.dataclasses.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessInDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(location: Location)

    @Query("SELECT * FROM locations_table")
    fun getSavedLocations(): Flow<List<Location>>

    @Delete
    suspend fun deleteLocation(location: Location)

}