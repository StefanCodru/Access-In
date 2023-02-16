package com.accessin.app.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.accessin.app.model.usecases.*
import com.accessin.app.model.util.LocationTypeConstants
import com.accessin.app.model.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccessInViewModel(
    private val app: Application,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val getLocationAccessibilityDetailsUseCase: GetLocationAccessibilityDetailsUseCase,
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getScrollableSectionsUseCase: GetScrollableSectionsUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val searchAccordingToQueryUseCase: SearchAccordingToQueryUseCase,
    private val searchForSpecificLocationTypeUseCase: SearchForSpecificLocationTypeUseCase
): AndroidViewModel(app) {


    // Local Datasource

    fun deleteLocation(location: Location) = viewModelScope.launch {
        deleteLocationUseCase.execute(location)
    }

    fun saveLocation(location: Location) = viewModelScope.launch {
        saveLocationUseCase.execute(location)
    }

    fun getSavedLocations() = liveData {
        getSavedLocationsUseCase.execute().collect {
            emit(it)
        }
    }


    // Remote DataSource

    private val _scrollableSections: MutableLiveData<UiState<List<ScrollableSection>>> = MutableLiveData()
    val scrollableSections: LiveData<UiState<List<ScrollableSection>>>
        get() = _scrollableSections

    fun getScrollableSections() = viewModelScope.launch(Dispatchers.IO) {

        try {
            if(isNetworkAvailable(app)){
                _scrollableSections.postValue(UiState.Loading)
                getScrollableSectionsUseCase.execute {
                    _scrollableSections.postValue(it)
                }
            } else {
                _scrollableSections.postValue(UiState.Error("No network connection"))
            }
        } catch(e: Exception){
            _scrollableSections.postValue(UiState.Error(e.message.toString()))
        }

    }


    private val _accessibilityDetails: MutableLiveData<UiState<LocationAccessibilityDetails?>> = MutableLiveData()
    val accessibilityDetails: LiveData<UiState<LocationAccessibilityDetails?>>
        get() = _accessibilityDetails

    fun getLocationAccessibilityData(locationId: String) = viewModelScope.launch(Dispatchers.IO) {

        try {
            if(isNetworkAvailable(app)){
                _accessibilityDetails.postValue(UiState.Loading)
                getLocationAccessibilityDetailsUseCase.execute(locationId) {
                    _accessibilityDetails.postValue(it)
                }
            } else {
                _accessibilityDetails.postValue(UiState.Error("No network connection"))
            }
        } catch(e: Exception){
            _accessibilityDetails.postValue(UiState.Error(e.message.toString()))
        }

    }

    private val _searchQueriedLocationList: MutableLiveData<UiState<List<Location>>> = MutableLiveData()
    val searchQueriedLocationList: LiveData<UiState<List<Location>>>
        get() = _searchQueriedLocationList

    fun searchAccordingToQuery(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {

        try {
            if(isNetworkAvailable(app)){
                _searchQueriedLocationList.postValue(UiState.Loading)
                searchAccordingToQueryUseCase.execute(searchQuery) {
                    _searchQueriedLocationList.postValue(it)
                }
            } else {
                _searchQueriedLocationList.postValue(UiState.Error("No network connection"))
            }
        } catch(e: Exception){
            _searchQueriedLocationList.postValue(UiState.Error(e.message.toString()))
        }

    }


    private val _specificLocationTypeList: MutableLiveData<UiState<List<Location>>> = MutableLiveData()
    val specificLocationTypeList: LiveData<UiState<List<Location>>>
        get() = _specificLocationTypeList

    fun searchForSpecificLocationType(locationType: String) = viewModelScope.launch(Dispatchers.IO) {

        try {
            if(isNetworkAvailable(app)){
                _specificLocationTypeList.postValue(UiState.Loading)
                searchForSpecificLocationTypeUseCase.execute(locationType) {
                    _specificLocationTypeList.postValue(it)
                }
            } else {
                _specificLocationTypeList.postValue(UiState.Error("No network connection"))
            }
        } catch(e: Exception){
            _specificLocationTypeList.postValue(UiState.Error(e.message.toString()))
        }

    }






    private fun isNetworkAvailable(context: Context?): Boolean{
        if(context == null) return false
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
        return result
    }


}