package com.accessin.app.model.util

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type

class MapTypeConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String?, Boolean?>? {
        val mapType: Type = object : TypeToken<Map<String?, Boolean?>?>() {}.getType()
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String?, Boolean?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }
}