package com.example.weatherapps.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherapps.API.MapApi
import com.example.weatherapps.API.MapApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
//import javax.inject.ViewModelInject
import android.content.Context
import com.example.weatherapps.Classes.Place
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


@HiltViewModel
class MapViewModel @Inject constructor(val mapApi: MapApi) : ViewModel() {
    // Use the mapApi in the ViewModel to interact with the maps functionality
    // ...

    /*constructor() : this(MapApiImpl(context, apiKey)) {
        // Initialize any additional dependencies or variables here if needed
    }*/

    private var onPlaceSelectedCallback: ((Place) -> Unit)? = null


    fun displayMap(mapView: MapView, onPlaceSelected: (Place) -> Unit) {
        onPlaceSelectedCallback = onPlaceSelected

        mapApi.displayMap (mapView){ latitude, longitude, placeName ->
            // Handle the place selection data here
            // ...
            val mapReadyCallback = OnMapReadyCallback { googleMap ->
                googleMap.setOnMapClickListener { latLng ->
                    val selectedPlace = Place(latLng.latitude, latLng.longitude, "Selected Place")
                    onPlaceSelectedCallback?.invoke(selectedPlace)

                    // Move the marker to the selected place
                    mapApi.moveMarkerToPlace(selectedPlace, mapView)

                }
            }

            mapView.getMapAsync(mapReadyCallback)

            Log.e("Details of place selected: ", "$latitude ")

        }
    }

    fun setInitialLocation(mapView: MapView,latitude: Double, longitude: Double) {
        val initialLatLng = LatLng(latitude, longitude)
        // Call the method in MapApi to set the initial marker
        mapApi.setInitialMarker(initialLatLng, mapView)
    }

    fun updateMarkerPosition(mapView: MapView, latitude: Double, longitude: Double) {
        val updatedLatLng = LatLng(latitude, longitude)
        mapApi.updateMarkerPosition(updatedLatLng, mapView)
    }

    fun getSelectedPlace(): Place? {
        return mapApi.getSelectedPlace()
    }





}




/*
@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    // Add any required data and business logic for the MapScreen

}*/
