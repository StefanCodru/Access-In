package com.accessin.app.viewmodel.hilt

import android.app.Application
import androidx.room.Room
import com.accessin.app.model.data.room.AccessInDao
import com.accessin.app.model.data.room.AccessInDatabase
import com.accessin.app.view.adapters.LocationDetailsAdapter
import com.accessin.app.view.adapters.SavedLocationsAdapter
import com.accessin.app.view.adapters.ScrollableSectionAdapter
import com.accessin.app.view.adapters.ScrollableSectionsAdapter
import com.accessin.app.view.adapters.SearchedLocationsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideScrollableSectionsAdapter(): ScrollableSectionsAdapter {
        return ScrollableSectionsAdapter()
    }

    @Singleton
    @Provides
    fun provideSavedLocationsAdapter(): SavedLocationsAdapter {
        return SavedLocationsAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchedLocationsAdapter(): SearchedLocationsAdapter {
        return SearchedLocationsAdapter()
    }

    @Singleton
    @Provides
    fun provideLocationDetailsAdapter(): LocationDetailsAdapter {
        return LocationDetailsAdapter()
    }


}