package com.accessin.app.model.data.datasources

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.data.firestore.FireStoreBackend
import com.accessin.app.model.util.UiState

class RemoteDataSourceImpl(private val fireStoreBackend: FireStoreBackend):RemoteDataSource {

    override suspend fun getLocationsNearUser(result: (UiState<ScrollableSection>) -> Unit) {
        fireStoreBackend.getLocationsNearUser(result)
    }

    override suspend fun getTopRatedLocations(result: (UiState<ScrollableSection>) -> Unit) {
        fireStoreBackend.getTopRatedLocations(result)
    }

    override suspend fun getLocationAccessibilityDetails(locationID: String, result: (UiState<LocationAccessibilityDetails?>) -> Unit) {
        fireStoreBackend.getLocationAccessibilityDetails(locationID, result)
    }

    override suspend fun searchForSpecificLocationType(locationType: String, result: (UiState<List<Location>>) -> Unit) {
        fireStoreBackend.searchForSpecificLocationType(locationType, result)
    }

    override suspend fun searchAccordingToQuery(searchQuery: String, result: (UiState<List<Location>>) -> Unit) {
        fireStoreBackend.searchAccordingToQuery(searchQuery, result)
    }
}