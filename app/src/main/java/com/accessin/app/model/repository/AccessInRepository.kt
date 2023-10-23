package com.accessin.app.model.repository

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.util.UiState
import kotlinx.coroutines.flow.Flow

interface AccessInRepository {

    suspend fun getLocationsNearUser(userLatitude: Double, userLongitude: Double, result: (UiState<ScrollableSection>) -> Unit)
    suspend fun getTopRatedLocations(result: (UiState<ScrollableSection>) -> Unit)
    suspend fun getLocationAccessibilityDetails(locationID: String, result: (UiState<LocationAccessibilityDetails?>) -> Unit)
    suspend fun searchForSpecificLocationType(locationType: String, result: (UiState<List<Location>>) -> Unit)
    suspend fun searchAccordingToQuery(searchQuery: String, result: (UiState<List<Location>>) -> Unit)

    suspend fun saveLocation(location: Location)
    suspend fun deleteLocation(location: Location)
    fun getSavedLocations(): Flow<List<Location>>

}