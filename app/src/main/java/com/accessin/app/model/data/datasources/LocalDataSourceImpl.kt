package com.accessin.app.model.data.datasources

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.room.AccessInDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val accessInDao: AccessInDao): LocalDataSource {
    override suspend fun saveLocation(location: Location) {
        accessInDao.saveLocation(location)
    }

    override suspend fun deleteLocation(location: Location) {
        accessInDao.deleteLocation(location)
    }

    override fun getSavedLocations(): Flow<List<Location>> {
        return accessInDao.getSavedLocations()
    }
}