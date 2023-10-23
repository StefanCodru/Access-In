package com.accessin.app.model.data.firestore

import android.util.Log
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.util.FireStoreFields
import com.accessin.app.model.util.ScrollableSectionsTypeConstants
import com.accessin.app.model.util.UiState
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class FireStoreBackendImpl(private val database: FirebaseFirestore): FireStoreBackend {

    private val TAG = "FireStoreBackendImpl"

    override suspend fun getLocationsNearUser(userLatitude: Double, userLongitude: Double, result: (UiState<ScrollableSection>) -> Unit) {
        val docRef = database.collection(FireStoreFields.TABLE_LOCATIONS)

        // Find cities within 5km of user
        val center = GeoLocation(userLatitude, userLongitude)
        val radiusInM = 5.0 * 1000.0

        // Each item in 'bounds' represents a startAt/endAt pair. We have to issue
        // a separate query for each pair. There can be up to 9 pairs of bounds
        // depending on overlap, but in most cases there are 4.
        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)

        val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()
        for (b in bounds) {
            val q = docRef
                .orderBy(FireStoreFields.GEOHASH_FIELD)
                .startAt(b.startHash)
                .endAt(b.endHash)

            tasks.add(q.get())
        }

        // Collect all the query results together into a single list
        Tasks.whenAllComplete(tasks)
            .addOnCompleteListener {
                val matchingDocs: MutableList<DocumentSnapshot> = ArrayList()
                for (task in tasks) {
                    Log.i(TAG, "getLocationsNearUser: this is the task ${task.result.documents}")

                    val snap = task.result
                    for (doc in snap!!.documents) {
                        val lat = doc.getDouble(FireStoreFields.LATITUDE_FIELD)!!
                        val lng = doc.getDouble(FireStoreFields.LONGITUDE_FIELD)!!

                        // We have to filter out a few false positives due to GeoHash
                        // accuracy, but most will match
                        val docLocation = GeoLocation(lat, lng)
                        val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                        if (distanceInM <= radiusInM) {
                            matchingDocs.add(doc)
                        }

                    }
                }

                val locationsList = arrayListOf<Location>()

                for (document in matchingDocs) {
                    val location = document.toObject(Location::class.java)
                    locationsList.add(location!!)
                }

                val section = ScrollableSection(ScrollableSectionsTypeConstants.NEAR_YOU, locationsList)
                result.invoke(UiState.Success(section))
            }
            .addOnFailureListener {
                result.invoke(UiState.Error(it.localizedMessage))
            }

    }

    override suspend fun getTopRatedLocations(result: (UiState<ScrollableSection>) -> Unit) {
        val docRef = database.collection(FireStoreFields.TABLE_LOCATIONS)

        docRef.orderBy(FireStoreFields.AREAS_INFO_FIELD + "." + FireStoreFields.GENERAL_RATING_FIELD, Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->

                val locationsList = arrayListOf<Location>()

                for (document in documents) {
                    val location = document.toObject(Location::class.java)
                    locationsList.add(location)
                }

                val section = ScrollableSection(ScrollableSectionsTypeConstants.TOP_RATED, locationsList)
                result.invoke(UiState.Success(section))
            }
            .addOnFailureListener { exception ->
                result.invoke(UiState.Error(exception.localizedMessage))
            }
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