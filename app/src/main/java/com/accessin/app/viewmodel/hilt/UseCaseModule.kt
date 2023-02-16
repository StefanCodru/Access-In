package com.accessin.app.viewmodel.hilt

import com.accessin.app.model.repository.AccessInRepository
import com.accessin.app.model.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetLocationAccessibilityDetailsUseCase(accessInRepository: AccessInRepository): GetLocationAccessibilityDetailsUseCase {
        return GetLocationAccessibilityDetailsUseCase(accessInRepository)
    }

    @Singleton
    @Provides
    fun providesGetScrollableSectionsUseCase(accessInRepository: AccessInRepository): GetScrollableSectionsUseCase {
        return GetScrollableSectionsUseCase(accessInRepository)
    }

    @Singleton
    @Provides
    fun providesSaveLocationsUseCase(accessInRepository: AccessInRepository): SaveLocationUseCase {
        return SaveLocationUseCase(accessInRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteLocationUseCase(accessInRepository: AccessInRepository): DeleteLocationUseCase {
        return DeleteLocationUseCase(accessInRepository)
    }

    @Singleton
    @Provides
    fun providesSearchAccordingToQueryUseCase(accessInRepository: AccessInRepository): SearchAccordingToQueryUseCase {
        return SearchAccordingToQueryUseCase(accessInRepository)
    }

    @Singleton
    @Provides
    fun providesSearchForSpecificLocationTypeUseCase(accessInRepository: AccessInRepository): SearchForSpecificLocationTypeUseCase {
        return SearchForSpecificLocationTypeUseCase(accessInRepository)
    }

    @Singleton
    @Provides
    fun providesGetSavedLocationsUseCase(accessInRepository: AccessInRepository): GetSavedLocationsUseCase {
        return GetSavedLocationsUseCase(accessInRepository)
    }


}