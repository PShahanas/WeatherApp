package com.example.weatherapps.ViewModels

//import com.example.weatherapps.Repository.GeoAddressRepositoryImpl
//import com.example.weatherapps.Repository.GeoaddressRepository
//import com.example.weatherapps.Repository.GeoAddressRepository

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapps.API.GeoAddressApi
import com.example.weatherapps.API.Place
import com.example.weatherapps.Classes.MapOverlay
import com.example.weatherapps.Classes.UserInput
import com.example.weatherapps.Location.LocationService
import com.example.weatherapps.Repository.GeoAddressRepository
import com.example.weatherapps.Repository.WeatherRepository
import com.example.weatherapps.ui.Weather.WeatherState
//import com.example.weatherapps.ui.Weather.getPlacePredictions
import com.example.weatherapps.utils.Resource
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@HiltViewModel
class SearchViewModel @Inject constructor(private val placesClient: PlacesClient
                                          //private val geoAddressRepository: GeoAddressRepository,
                                          //private val geoAddressApi: GeoAddressApi,
                                          //private val userInput: UserInput,
                                          //private val repository: WeatherRepository

) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

       // private val _searchText = MutableStateFlow("")
        //val searchText = _searchText.asStateFlow()

        //private val _isSearching = MutableStateFlow(false)
        //val isSearching = _isSearching.asStateFlow()

    //private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    //val suggestions: StateFlow<List<String>> = _suggestions

      //  private val _places = MutableStateFlow<List<Place>>(emptyList())
        //val places: StateFlow<List<Place>> = _places

        //private val coroutineScope = CoroutineScope(Dispatchers.IO)

   //private val _placePredictions = mutableStateListOf<AutocompletePrediction>()
    //val placePredictions: List<AutocompletePrediction> get() = _placePredictions

    /*fun fetchPlacePredictions(query: String) {
        viewModelScope.launch {
            try {
                val predictions = getPlacePredictions(query, placesClient)
                _placePredictions.clear()
                _placePredictions.addAll(predictions)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }*/



        /*fun fetchPlaces(query: UserInput) {
            coroutineScope.launch {
                try {
                    _isSearching.emit(true)
                    val placesList = geoAddressRepository.searchPlaces(query)
                    val suggestionsList = placesList.map { place -> place.formattedAddress }
                    _suggestions.emit(suggestionsList)
                    _places.emit(placesList)
                } catch (e: Exception) {
                    // Handle the exception
                    e.printStackTrace()
                } finally {
                    _isSearching.emit(false)
                }
            }
        }*/

    /*fun searchPlaces(query: String) {
        // Perform the autocomplete search using PlacesClient
        //val placesClient = Places.createClient(application)

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                // Update placePredictions with the new predictions list
                _placePredictions.clear()
                _placePredictions.addAll(response.autocompletePredictions)
            }
            .addOnFailureListener { exception ->
                // Handle failure, e.g., show an error message
                Log.e("SearchViewModel", "Autocomplete search failed: ${exception.localizedMessage}")
            }
    }*/

        /*fun onSearchTextChange(text: UserInput) {
            //userInput.address = text.address
            //_searchText.value = text.address

            userInput.address = text.address
            fetchPlaces(userInput)

            //fetchPlaces(text)


        }*/

    private val _placePredictions = MutableStateFlow<List<AutocompletePrediction>>(emptyList())
    val placePredictions: StateFlow<List<AutocompletePrediction>> = _placePredictions

    fun fetchPlacePredictions(query: String) {
        /*viewModelScope.launch {
            try {
                val predictions = getPlacePredictions(query)
                _placePredictions.value = predictions
            } catch (e: Exception) {
                Log.d("fetch Predictions","Not able to fetch predicti")
                // Handle exception
            }
        }*/
        viewModelScope.launch {
            val result = getPlacePredictions(query)
            when (result) {
                is GetPlacePredictionsResult.Success -> {
                    val predictions = result.predictions
                    _placePredictions.value = predictions
                }
                is GetPlacePredictionsResult.Error -> {
                    val exception = result.exception
                    Log.d("fetch Predictions", "Not able to fetch predictions: $exception")
                    // Handle exception
                }
            }

        }
    }

    sealed class GetPlacePredictionsResult {
        data class Success(val predictions: List<AutocompletePrediction>) : GetPlacePredictionsResult()
        data class Error(val exception: Exception) : GetPlacePredictionsResult()
    }


    @SuppressLint("SuspiciousIndentation")
    private suspend fun getPlacePredictions(query: String): GetPlacePredictionsResult  =
        suspendCancellableCoroutine { continuation ->
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build()

                placesClient.findAutocompletePredictions(request)
                    .addOnSuccessListener { response ->
                        continuation.resume(GetPlacePredictionsResult.Success(response.autocompletePredictions))
                        Log.d("Predictions: ", "${response.autocompletePredictions}")
                    }
                    .addOnFailureListener { exception ->
                        continuation.resume(GetPlacePredictionsResult.Error(exception))
                    }

        }


    fun onLocationUpdated(location: LatLng) {

        Log.e("Cordinates received in p: "," SUCCESS : Longitude: ${location.longitude} , Latitude: ${location.latitude}")

    // Update the userInput or perform any necessary actions

        //LocationService.setCurrentLatitude(location.latitude)
            //LocationService.setCurrentLongitude(location.longitude)

        //LocationService.getCurrentLatitude()
        //LocationService.getCurrentLongitude()


    }


    }














