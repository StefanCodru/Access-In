package com.accessin.app.viewmodel.hilt

import android.app.Application
import com.accessin.app.model.usecases.*
import com.accessin.app.viewmodel.AccessInViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        deleteLocationUseCase: DeleteLocationUseCase,
        getLocationAccessibilityDetailsUseCase: GetLocationAccessibilityDetailsUseCase,
        getSavedLocationsUseCase: GetSavedLocationsUseCase,
        getScrollableSectionsUseCase: GetScrollableSectionsUseCase,
        saveLocationUseCase: SaveLocationUseCase,
        searchAccordingToQueryUseCase: SearchAccordingToQueryUseCase,
        searchForSpecificLocationTypeUseCase: SearchForSpecificLocationTypeUseCase
    ): AccessInViewModelFactory {
        return AccessInViewModelFactory(
            app,
            deleteLocationUseCase,
            getLocationAccessibilityDetailsUseCase,
            getSavedLocationsUseCase,
            getScrollableSectionsUseCase,
            saveLocationUseCase,
            searchAccordingToQueryUseCase,
            searchForSpecificLocationTypeUseCase)
    }

}