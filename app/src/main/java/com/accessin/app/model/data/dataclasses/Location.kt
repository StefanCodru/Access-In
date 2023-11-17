package com.accessin.app.model.data.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.accessin.app.model.util.LocationDetailsTypeConverter
import com.accessin.app.model.util.MapTypeConverter
import com.google.firebase.firestore.PropertyName
import java.io.Serializable

@Entity(tableName = "locations_table")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int? = null,
    var address: String = "",
    @get:PropertyName("general_rating") @set:PropertyName("general_rating")
    var generalRating: Float = 0.0f,
    val latitude: Double,
    val longitude: Double,
    @get:PropertyName("image_url") @set:PropertyName("image_url")
    var imageURL: String = "",
    @TypeConverters(MapTypeConverter::class)
    @get:PropertyName("location_tags") @set:PropertyName("location_tags")
    var locationTags: Map<String, Boolean>?,
    @get:PropertyName("location_type") @set:PropertyName("location_type")
    var type: String = "",
    @get:PropertyName("location_name") @set:PropertyName("location_name")
    var name: String = "",
    @get:PropertyName("areasInfo") @set:PropertyName("areasInfo")
    @TypeConverters(LocationDetailsTypeConverter::class)
    var locationDetails: Map<String, LocationAccessibilityDetails>?,
): Serializable {
    constructor() : this(
        null,
        "",
        0.0f,
        0.0,
        0.0,
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
        "",
        null
    )
}