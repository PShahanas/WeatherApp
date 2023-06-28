package com.example.weatherapps.API

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.weatherapps.Classes.Place
import com.example.weatherapps.Location.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject
import javax.inject.Named

interface MapApi {

    fun displayMap(mapView: MapView, onPlaceSelected: (latitude: Double, longitude: Double, placeName: String) -> Unit)
    fun setInitialMarker(latLng: LatLng, mapView: MapView)
    fun updateMarkerPosition(latLng: LatLng, mapView: MapView)
    //fun getSelectedPlace(): Place?

    fun moveMarkerToPlace(latLng: LatLng, mapView: MapView)

    fun setOnMarkerPositionChangedListener(listener: (LatLng) -> Unit)

    fun setMarker(latLng: LatLng, mapView: MapView)
    fun getClickedLocation(): Pair<Double, Double>?
}
    //fun moveMarkerToPlace(latLng: LatLng,view: View, mapView: MapView)
    //fun getSelectedPlace(): com.example.weatherapps.Classes.Place
    // Define the methods for interacting with the maps API
    // ...


class MapApiImpl @Inject constructor(
    private val context: Context,
    @Named("mapsApiKey") private val apiKey: String,
    //private val mapFragment: SupportMapFragment
) : MapApi {

    companion object{
        var MapClicked: Boolean = false
    }

    private var clickedLocation: Pair<Double, Double>? = null

    private var onPlaceSelectedCallback: ((latitude: Double, longitude: Double, placeName: String) -> Unit)? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var selectedPlaceMarker: Marker? = null
    private var onMarkerPositionChangedListener: ((LatLng) -> Unit)? = null

    override fun setOnMarkerPositionChangedListener(listener: (LatLng) -> Unit) {
        onMarkerPositionChangedListener = listener
    }


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
        mapView.getMapAsync { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                selectedPlaceMarker?.remove()
                val markerOptions = MarkerOptions().position(latLng).title("Selected Place")
                selectedPlaceMarker = googleMap.addMarker(markerOptions)
                //googleMap.clear()
                //googleMap.addMarker(markerOptions)

                val selectedPlace = Place(latLng.latitude, latLng.longitude, "Selected Place")
                onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)

                //moveMarkerToPlace(selectedPlace, mapView)
            }

            googleMap.setOnMarkerClickListener { marker ->
                val selectedPlace = marker.tag as? Place
                if (selectedPlace != null) {
                    onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
                }

                Log.i("Click on screen working: ", "Yes")

                true
            }
        }

        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync {}

        Toast.makeText(context, "Displaying Map", Toast.LENGTH_SHORT).show()
    }

     @SuppressLint("PotentialBehaviorOverride")
     override fun setInitialMarker(latLng: LatLng, mapView: MapView) {
       // val mapView = MapView(context)
         //val zoomLevel = 20.0f

         //val sydney = LatLng(-34.0, 151.0)
         val stokeOnTrent = LatLng(53.025780,-2.177390)
         val currentPosition = LatLng(latLng.latitude, latLng.longitude)
         //val stokeCobrdige = LatLng(53.029380,-2.188740)


        mapView.getMapAsync { googleMap ->


            googleMap.addMarker(MarkerOptions().position(stokeOnTrent).title("Stoke Center"))
            val marker = googleMap.addMarker(
                MarkerOptions().position(currentPosition).title("Current Position")
            )
            //googleMap.addMarker(MarkerOptions().position(stokeCobrdige).title("Cobridge - Stoke"))
            googleMap.minZoomLevel
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15.0f))
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.setOnMapClickListener {
                Log.i("Clicked map:", " hURRAY     ")
                marker?.apply {
                    marker.position = it
                    MapClicked = true
                    LocationService.currentLatitude = it.latitude
                    LocationService.currentLongitude = it.longitude
                    Log.e("Current position in map: ","${LocationService.currentLatitude} + ${LocationService.currentLongitude}")

                }
                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it,15.0f))

            }

            googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragStart(marker: Marker) {}

                override fun onMarkerDrag(marker: Marker) {}

                override fun onMarkerDragEnd(marker: Marker) {
                    val newPosition = marker.position
                    onMarkerPositionChangedListener?.invoke(newPosition)
                }
            })

        }

    }

    override fun setMarker(latLng: LatLng, mapView: MapView) {
        // Implementation to set the marker on the map
        val currentPosition = LatLng(latLng.latitude, latLng.longitude)
        var clickedLocation: Pair<Double, Double>? = null
        mapView.getMapAsync { googleMap ->
            val marker = googleMap.addMarker(
                MarkerOptions().position(currentPosition).title("Current Position")
            )
            googleMap.setOnMapClickListener {
                Log.i("Clicked map:", " hURRAY     ")
                marker?.apply {
                    marker.position = it
                    MapClicked = true

                    clickedLocation = Pair(it.latitude, it.longitude)

                }
                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it,15.0f))

            }

        }
        // ...

        // Update the clicked location
    }

    fun getLatofplace(){

    }

    override fun getClickedLocation(): Pair<Double, Double>? {
        return clickedLocation
    }

    override fun updateMarkerPosition(latLng: LatLng, mapView: MapView) {
        mapView.getMapAsync { googleMap ->
            val marker = googleMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
            marker?.apply {
                remove()
                googleMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.0f))


            /*googleMap.setOnMapClickListener {
                Log.i("Lat n lng points clicked: ","${it.latitude} + ${it.longitude}")
                googleMap.addMarker()
            }*/


        }
    }

     /*override fun getSelectedPlace(): Place? {
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
    }*/

    override fun moveMarkerToPlace(latLng: LatLng, mapView: MapView) {
        mapView.getMapAsync { googleMap ->
            selectedPlaceMarker?.remove()
            val markerOptions = MarkerOptions().position(latLng).title("Selected Place")
            //googleMap.clear()
            //googleMap.addMarker(markerOptions)
            selectedPlaceMarker = googleMap.addMarker(markerOptions)

            val selectedPlace = Place(latLng.latitude, latLng.longitude, "Selected Place")
            onPlaceSelectedCallback?.invoke(selectedPlace.latitude, selectedPlace.longitude, selectedPlace.name)
        }
    }

    /*override fun moveMarkerToPlace(latLng: LatLng,view: View, mapView: MapView) {
        mapView.getMapAsync { googleMap ->
            googleMap.clear()

            googleMap.setOnMapClickListener {
                latLng ->
                googleMap.clear()

                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Clicked Location")

                googleMap.addMarker(markerOptions)
            }


            //val view = View.
            //val markerOptions = MarkerOptions()
                //.position(LatLng(place.latitude, place.longitude))
                //.title(place.name)
              //  .position
            //googleMap.addMarker(place)
            //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.position, 2.0f))
        }
    }*/


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


