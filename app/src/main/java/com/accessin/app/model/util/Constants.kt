package com.accessin.app.model.util

object FireStoreFields {
    val TABLE_LOCATIONS = "locations"
    val COLLECTION_ACCESSIBILITY_DATA = "accessibility_data"
    val AREAS_INFO_FIELD = "areasInfo"
    val GENERAL_RATING_FIELD = "general_rating"
    val LATITUDE_FIELD = "latitude"
    val LONGITUDE_FIELD = "longitude"
    val GEOHASH_FIELD = "geohash"
}

object LocationTypeConstants {
    val RESTAURANT = "Restaurant"
    val COFFEE_SHOP = "Coffee Shop"
    val PHARMACY = "Pharmacy"
    val TOURIST_ATTRACTION = "Tourist Attraction"
    val HOTEL = "Hotel"
    val TRANSPORTATION = "Transportation"
}

object ScrollableSectionsTypeConstants {
    val TOP_RATED = "Top Rated"
    val NEAR_YOU = "Near You"
}
