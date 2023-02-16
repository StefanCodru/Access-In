package com.accessin.app.model.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.util.GeoPointTypeConverter
import com.accessin.app.model.util.MapTypeConverter

@Database(entities = [Location::class], version = 1)
@TypeConverters(GeoPointTypeConverter::class, MapTypeConverter::class)
abstract class AccessInDatabase : RoomDatabase() {

    abstract fun getAccessInDao(): AccessInDao

}