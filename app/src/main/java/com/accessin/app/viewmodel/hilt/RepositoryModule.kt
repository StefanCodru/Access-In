package com.accessin.app.viewmodel.hilt

import com.accessin.app.model.data.datasources.LocalDataSource
import com.accessin.app.model.data.datasources.RemoteDataSource
import com.accessin.app.model.repository.AccessInRepository
import com.accessin.app.model.repository.AccessInRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesAccessInRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): AccessInRepository {
        return AccessInRepositoryImpl(remoteDataSource, localDataSource)
    }

}