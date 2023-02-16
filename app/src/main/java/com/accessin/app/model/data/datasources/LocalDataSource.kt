package com.accessin.app.model.data.datasources

import com.accessin.app.model.data.dataclasses.Location
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveLocation(location: Location)
    suspend fun deleteLocation(location: Location)
    fun getSavedLocations(): Flow<List<Location>>
}