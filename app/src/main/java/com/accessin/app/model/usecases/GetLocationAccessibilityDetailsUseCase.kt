package com.accessin.app.model.usecases

import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.repository.AccessInRepository
import com.accessin.app.model.util.UiState

class GetLocationAccessibilityDetailsUseCase(val accessInRepository: AccessInRepository) {

    suspend fun execute(locationID: String, result: (UiState<LocationAccessibilityDetails?>) -> Unit) {
        accessInRepository.getLocationAccessibilityDetails(locationID, result)
    }

}