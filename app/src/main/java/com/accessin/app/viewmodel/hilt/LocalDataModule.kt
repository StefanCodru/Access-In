package com.accessin.app.viewmodel.hilt

import com.accessin.app.model.data.datasources.LocalDataSource
import com.accessin.app.model.data.datasources.LocalDataSourceImpl
import com.accessin.app.model.data.room.AccessInDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(accessInDao: AccessInDao): LocalDataSource {
        return LocalDataSourceImpl(accessInDao)
    }

}