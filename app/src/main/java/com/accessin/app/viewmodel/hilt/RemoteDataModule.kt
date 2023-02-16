package com.accessin.app.viewmodel.hilt

import com.accessin.app.model.data.datasources.RemoteDataSource
import com.accessin.app.model.data.datasources.RemoteDataSourceImpl
import com.accessin.app.model.data.firestore.FireStoreBackend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesRemoteDataSource(fireStoreBackend: FireStoreBackend): RemoteDataSource{
        return RemoteDataSourceImpl(fireStoreBackend)
    }

}