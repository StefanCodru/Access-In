package com.accessin.app.model.usecases

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.repository.AccessInRepository
import kotlinx.coroutines.flow.Flow

class GetSavedLocationsUseCase(val accessInRepository: AccessInRepository) {

    fun execute(): Flow<List<Location>> {
        return accessInRepository.getSavedLocations()
    }

}