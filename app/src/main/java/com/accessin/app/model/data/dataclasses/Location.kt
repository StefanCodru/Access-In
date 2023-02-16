package com.accessin.app.model.data.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.accessin.app.model.util.GeoPointTypeConverter
import com.accessin.app.model.util.MapTypeConverter
import com.google.firebase.firestore.GeoPoint

@Entity(tableName = "locations_table")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int? = null,
    var address: String?,
    val general_rating: Double?,
    @TypeConverters(GeoPointTypeConverter::class)
    val geoPoint: GeoPoint?,
    val image_url: String?,
    @TypeConverters(MapTypeConverter::class)
    val location_tags: Map<String, Boolean>?,
    val location_type: String?,
    val name: String?
) {
    constructor() : this(
        null,
        "",
        0.0,
        GeoPoint(0.0, 0.0),
        "",
        mapOf(
            "shopping_tag" to false,
            "entertainment_tag" to false,
            "health_tag" to false,
            "food_tag" to false,
            "beauty_tag" to false,
            "education_tag" to false,
            "outdoor_tag" to false,
        ),
        "",
        ""
    )
}