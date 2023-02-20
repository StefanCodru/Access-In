package com.accessin.app.model.data.firestore

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.util.UiState

interface FireStoreBackend {
    suspend fun getLocationsNearUser(result: (UiState<ScrollableSection>) -> Unit)
    suspend fun getTopRatedLocations(result: (UiState<ScrollableSection>) -> Unit)
    suspend fun getLocationAccessibilityDetails(locationID: String, result: (UiState<LocationAccessibilityDetails?>) -> Unit)
    suspend fun searchForSpecificLocationType(locationType: String, result: (UiState<List<Location>>) -> Unit)
    suspend fun searchAccordingToQuery(searchQuery: String, result: (UiState<List<Location>>) -> Unit)
}