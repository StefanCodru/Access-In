package com.accessin.app.model.usecases

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.repository.AccessInRepository
import com.accessin.app.model.util.UiState

class SearchForSpecificLocationTypeUseCase(val accessInRepository: AccessInRepository) {

    suspend fun execute(locationType: String, result: (UiState<List<Location>>) -> Unit){
        accessInRepository.searchForSpecificLocationType(locationType, result)
    }

}