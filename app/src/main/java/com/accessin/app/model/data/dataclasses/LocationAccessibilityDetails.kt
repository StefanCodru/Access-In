package com.accessin.app.model.data.dataclasses

data class LocationAccessibilityDetails(
    val entrance_description: String?,
    val entrance_auditory_rating: Double?,
    val entrance_visual_rating: Double?,
    val entrance_mobility_rating: Double?,
    val interior_description: String?,
    val interior_auditory_rating: Double?,
    val interior_visual_rating: Double?,
    val interior_mobility_rating: Double?,
    val toilet_description: String?,
    val toilet_auditory_rating: Double?,
    val toilet_visual_rating: Double?,
    val toilet_mobility_rating: Double?
) {
    constructor() : this(
        "",
        0.0,
        0.0,
        0.0,
        "",
        0.0,
        0.0,
        0.0,
        "",
        0.0,
        0.0,
        0.0
    )
}