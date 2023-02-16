package com.accessin.app.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.accessin.app.model.usecases.*

class AccessInViewModelFactory(
    private val app: Application,
    val deleteLocationUseCase: DeleteLocationUseCase,
    val getLocationAccessibilityDetailsUseCase: GetLocationAccessibilityDetailsUseCase,
    val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    val getScrollableSectionsUseCase: GetScrollableSectionsUseCase,
    val saveLocationUseCase: SaveLocationUseCase,
    val searchAccordingToQueryUseCase: SearchAccordingToQueryUseCase,
    val searchForSpecificLocationTypeUseCase: SearchForSpecificLocationTypeUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(AccessInViewModel::class.java)){
            return AccessInViewModel(
                app,
                deleteLocationUseCase,
                getLocationAccessibilityDetailsUseCase,
                getSavedLocationsUseCase,
                getScrollableSectionsUseCase,
                saveLocationUseCase,
                searchAccordingToQueryUseCase,
                searchForSpecificLocationTypeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")

    }
}