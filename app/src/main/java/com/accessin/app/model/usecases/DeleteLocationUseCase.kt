package com.accessin.app.model.usecases

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.repository.AccessInRepository

class DeleteLocationUseCase(val accessInRepository: AccessInRepository)  {

    suspend fun execute(location: Location){
        accessInRepository.deleteLocation(location)
    }

}