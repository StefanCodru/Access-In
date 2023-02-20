package com.accessin.app.model.usecases

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.repository.AccessInRepository
import com.accessin.app.model.util.UiState
import kotlinx.coroutines.*
import java.util.concurrent.CountDownLatch

class GetScrollableSectionsUseCase(val accessInRepository: AccessInRepository) {

    suspend fun execute(result: (UiState<List<ScrollableSection>>) -> Unit) {

        val countDownLatch = CountDownLatch(2)
        val scrollableSectionsList = mutableListOf<ScrollableSection>()


        CoroutineScope(Dispatchers.IO).launch {

            val nearbyLocationsAsync = async { accessInRepository.getLocationsNearUser { section ->

                if(section is UiState.Success) {
                    scrollableSectionsList.add(section.data)
                }

                countDownLatch.countDown()

            }}

            val topRatedLocationsAsync = async { accessInRepository.getTopRatedLocations { section ->

                if(section is UiState.Success) {
                    scrollableSectionsList.add(section.data)
                }

                countDownLatch.countDown()

            }}

            nearbyLocationsAsync.await()
            topRatedLocationsAsync.await()

        }

        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            countDownLatch.await()

            if(scrollableSectionsList.isNotEmpty()){
                result.invoke(UiState.Success(scrollableSectionsList.toList()))
            } else {
                result.invoke(UiState.Error("Error in retrieving data"))
            }
        }



    }

}