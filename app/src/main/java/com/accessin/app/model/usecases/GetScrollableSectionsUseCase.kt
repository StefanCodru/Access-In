package com.accessin.app.model.usecases

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.repository.AccessInRepository
import com.accessin.app.model.util.UiState

class GetScrollableSectionsUseCase(val accessInRepository: AccessInRepository) {

    suspend fun execute(result: (UiState<List<ScrollableSection>>) -> Unit) {
        accessInRepository.getScrollableSections(result)
    }

}