package com.accessin.app.viewmodel.hilt

import com.accessin.app.model.data.datasources.LocalDataSource
import com.accessin.app.model.data.datasources.RemoteDataSource
import com.accessin.app.model.data.firestore.FireStoreBackend
import com.accessin.app.model.data.firestore.FireStoreBackendImpl
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
class FireStoreBackendModule {

    @Singleton
    @Provides
    fun providesFireStoreBackend(database: FirebaseFirestore): FireStoreBackend {
        return FireStoreBackendImpl(database)
    }

}