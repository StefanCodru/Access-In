package com.accessin.app.model.data.dataclasses

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

data class LocationAccessibilityDetails(
    @get:PropertyName("details_title") @set:PropertyName("details_title")
    var title: String = "",
    @get:PropertyName("details_description") @set:PropertyName("details_description")
    var description: String = "",
    @get:PropertyName("details_auditory_rating") @set:PropertyName("details_auditory_rating")
    var auditoryRating: Double = 0.0,
    @get:PropertyName("details_visual_rating") @set:PropertyName("details_visual_rating")
    var visualRating: Double = 0.0,
    @get:PropertyName("details_mobility_rating") @set:PropertyName("details_mobility_rating")
    var mobilityRating: Double = 0.0,
): Serializable {
    constructor() : this(
        "",
        "",
        0.0,
        0.0,
        0.0,
    )
}