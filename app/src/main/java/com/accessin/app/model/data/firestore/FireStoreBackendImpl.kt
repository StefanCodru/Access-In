package com.accessin.app.model.data.firestore

import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.util.FireStoreFields
import com.accessin.app.model.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreBackendImpl(private val database: FirebaseFirestore): FireStoreBackend {

    private val TAG = "FireStoreBackendImpl"

    override suspend fun getScrollableSections(result: (UiState<List<ScrollableSection>>) -> Unit) {
        TODO("Change this and create many different functions for every scrollable section type")
    }

    override suspend fun getLocationAccessibilityDetails(locationID: String, result: (UiState<LocationAccessibilityDetails?>) -> Unit) {
        val docRef = database
            .collection(FireStoreFields.TABLE_LOCATIONS)
            .document(locationID)
            .collection(FireStoreFields.COLLECTION_ACCESSIBILITY_DATA)
            .document("data")

        docRef.get().addOnSuccessListener { document ->
                if (document != null) {

                    val accessibilityDetails = document.toObject(LocationAccessibilityDetails::class.java)
                    result.invoke(UiState.Success(accessibilityDetails))

                } else {
                    result.invoke(UiState.Error("Error in finding location!"))
                }
            }
            .addOnFailureListener { exception ->
                result.invoke(UiState.Error(exception.localizedMessage))
            }

    }

    override suspend fun searchForSpecificLocationType(locationType: String, result: (UiState<List<Location>>) -> Unit) {
        val docRef = database.collection(FireStoreFields.TABLE_LOCATIONS)

        docRef.whereEqualTo("location_type", locationType)
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->
                val locationsList = arrayListOf<Location>()

                for (document in documents) {
                    val location = document.toObject(Location::class.java)
                    locationsList.add(location)
                }

                result.invoke(UiState.Success(locationsList))
            }
            .addOnFailureListener { exception ->
                result.invoke(UiState.Error(exception.localizedMessage))
            }

    }

    override suspend fun searchAccordingToQuery(searchQuery: String, result: (UiState<List<Location>>) -> Unit) {
        val docRef = database.collection(FireStoreFields.TABLE_LOCATIONS)

        docRef.whereGreaterThanOrEqualTo("name", searchQuery)
            .whereLessThanOrEqualTo("name", searchQuery + "\uf8ff")
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->
                val locationsList = arrayListOf<Location>()

                for (document in documents) {
                    val location = document.toObject(Location::class.java)
                    locationsList.add(location)
                }

                result.invoke(UiState.Success(locationsList))
            }
            .addOnFailureListener { exception ->
                result.invoke(UiState.Error(exception.localizedMessage))
            }
    }

}