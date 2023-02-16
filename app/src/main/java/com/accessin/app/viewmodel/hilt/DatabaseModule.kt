package com.accessin.app.viewmodel.hilt

import android.app.Application
import androidx.room.Room
import com.accessin.app.model.data.room.AccessInDao
import com.accessin.app.model.data.room.AccessInDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(app: Application): AccessInDatabase{
        return Room.databaseBuilder(app, AccessInDatabase::class.java, "accessin_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesArticleDao(accessInDatabase: AccessInDatabase): AccessInDao{
        return accessInDatabase.getAccessInDao()
    }
}