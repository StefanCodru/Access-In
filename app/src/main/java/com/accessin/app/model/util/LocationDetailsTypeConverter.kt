package com.accessin.app.model.util

import androidx.room.TypeConverter
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class LocationDetailsTypeConverter {

    @TypeConverter
    fun mapToString(value: Map<String, LocationAccessibilityDetails>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToMap(value: String?): Map<String, LocationAccessibilityDetails>? {
        if (value == null) return null
        val mapType = object : TypeToken<Map<String, LocationAccessibilityDetails>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    /*
    @TypeConverter
    fun fromString(value: String?): Map<String?, Boolean?>? {
        val mapType: Type = object : com.google.common.reflect.TypeToken<Map<String?, Boolean?>?>() {}.getType()
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String?, Boolean?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }

     */
}