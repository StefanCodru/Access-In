package com.accessin.app.model.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.util.MapTypeConverter
import com.accessin.app.model.util.LocationDetailsTypeConverter

@Database(entities = [Location::class], version = 1)
@TypeConverters(MapTypeConverter::class, LocationDetailsTypeConverter::class)
abstract class AccessInDatabase : RoomDatabase() {

    abstract fun getAccessInDao(): AccessInDao

}