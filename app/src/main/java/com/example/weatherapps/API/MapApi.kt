package com.example.weatherapps.API

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject
import javax.inject.Named
import com.example.weatherapps.Classes.Place
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

interface MapApi {

    fun displayMap(mapView: MapView, onPlaceSelected: (latitude: Double, longitude: Double, placeName: String) -> Unit)
    fun setInitialMarker(latLng: LatLng, mapView: MapView)
    fun updateMarkerPosition(latLng: LatLng, mapView: MapView)
    fun getSelectedPlace(): Place?
    fun moveMarkerToPlace(place: Place, mapView: MapView)
    //fun getSelectedPlace(): com.example.weatherapps.Classes.Place
    // Define the methods for interacting with the maps API
    // ...
}

class MapApiImpl @Inject constructor(
    private val context: Context,
    @Named("mapsApiKey") private val apiKey: String,
    //private val mapFragment: SupportMapFragment
) : MapApi {

    private var onPlaceSelectedCallback: ((latitude: Double, longitude: Double, placeName: String) -> Unit)? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
    override fun displayMap(mapView: MapView,onPlaceSelected: (latitude: Double, longitude: Double, placeName: String) -> Unit) {
        onPlaceSelectedCallback = onPlaceSelected

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { lastLocation ->
                    val initialLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                    setInitialMarker(initialLatLng, mapView)
                }
            }
        }

        //val mapView = MapView(context)
        mapView.getMapAsync { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                val markerOptions = MarkerOptions().position(latLng).title("Selected Place")
                googleMap.clear()
                googleMap.addMarker(markerOptions)

                val selectedPlace = Place(latLng.latitude, latLng.longitude, "Selected Place")
                onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
            }

            googleMap.setOnMarkerClickListener { marker ->
                val selectedPlace = marker.tag as? Place
                if (selectedPlace != null) {
                    onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
                }

                true
            }
        }

        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync {}

        Toast.makeText(context, "Displaying Map", Toast.LENGTH_SHORT).show()
    }

     override fun setInitialMarker(latLng: LatLng, mapView: MapView) {
       // val mapView = MapView(context)
        mapView.getMapAsync { googleMap ->
            googleMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2.0f))
        }
    }

    override fun updateMarkerPosition(latLng: LatLng, mapView: MapView) {
        mapView.getMapAsync { googleMap ->
            val marker = googleMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
            marker?.apply {
                remove()
                googleMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2.0f))
        }
    }

     override fun getSelectedPlace(): Place? {
        val mapReadyCallback = OnMapReadyCallback { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                val markerOptions = MarkerOptions().position(latLng).title("Selected Place")
                googleMap.clear()
                googleMap.addMarker(markerOptions)

                val selectedPlace = Place(
                    latLng.latitude, latLng.longitude, "Selected Place"
                    //latLng.latitude.toString(), latLng.longitude
                )
                onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
            }
        }

        //mapFragment.getMapAsync(mapReadyCallback)

        return null
    }

    override fun moveMarkerToPlace(place: Place, mapView: MapView) {
        mapView.getMapAsync { googleMap ->
            googleMap.clear()
            val markerOptions = MarkerOptions()
                .position(LatLng(place.latitude, place.longitude))
                .title(place.name)
            googleMap.addMarker(markerOptions)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.position, 2.0f))
        }
    }


}

/*class MapApiImpl @Inject constructor(
    private val context: Context,
    @Named("mapsApiKey") private val apiKey: String,
    //private val mapFragment: SupportMapFragment
) : MapApi {

    private var onPlaceSelectedCallback: ((latitude: Double, longitude: Double, placeName: String) -> Unit)? = null

    @SuppressLint("PotentialBehaviorOverride")
    override fun displayMap(onPlaceSelected: (latitude: Double, longitude: Double, placeName: String) -> Unit) {
        onPlaceSelectedCallback = onPlaceSelected

        val mapView = MapView(context)
        mapView.getMapAsync { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                val markerOptions = MarkerOptions().position(latLng).title("Selected Place")
                googleMap.clear()
                googleMap.addMarker(markerOptions)


                val selectedPlace = Place(latLng.latitude, latLng.longitude, "Selected Place")
                onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
            }

            googleMap.setOnMarkerClickListener { marker ->
                val selectedPlace = marker.tag as? Place
                if (selectedPlace != null) {
                    onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
                }

                true
            }


        }

        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync {}

        Toast.makeText(context, "Displaying Map", Toast.LENGTH_SHORT).show()
    }

    private fun getSelectedPlace(): Place? {
        val mapReadyCallback = OnMapReadyCallback { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                val markerOptions = MarkerOptions().position(latLng).title("Selected Place")
                googleMap.clear()
                googleMap.addMarker(markerOptions)

                val selectedPlace = Place(
                    latLng.latitude, latLng.longitude, "Selected Place"
                    //latLng.latitude.toString(), latLng.longitude
                )
                onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
            }
        }

        //mapFragment.getMapAsync(mapReadyCallback)

        return null
    }


}*/


