package com.accessin.app.model.repository

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.data.datasources.LocalDataSource
import com.accessin.app.model.data.datasources.RemoteDataSource
import com.accessin.app.model.util.UiState
import com.google.android.gms.common.api.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class AccessInRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): AccessInRepository {

    override suspend fun getScrollableSections(result: (UiState<List<ScrollableSection>>) -> Unit) {
        remoteDataSource.getScrollableSections(result)
    }

    override suspend fun getLocationAccessibilityDetails(locationID: String, result: (UiState<LocationAccessibilityDetails?>) -> Unit) {
        remoteDataSource.getLocationAccessibilityDetails(locationID, result)
    }

    override suspend fun searchForSpecificLocationType(locationType: String, result: (UiState<List<Location>>) -> Unit) {
        remoteDataSource.searchForSpecificLocationType(locationType, result)
    }

    override suspend fun searchAccordingToQuery(searchQuery: String, result: (UiState<List<Location>>) -> Unit) {
        remoteDataSource.searchAccordingToQuery(searchQuery, result)
    }

    override suspend fun saveLocation(location: Location) {
        localDataSource.saveLocation(location)
    }

    override suspend fun deleteLocation(location: Location) {
        localDataSource.deleteLocation(location)
    }

    override fun getSavedLocations(): Flow<List<Location>> {
        return localDataSource.getSavedLocations()
    }




}