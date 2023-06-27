package com.example.weatherapps.ui.Weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.example.weatherapps.API.MapApi
import com.example.weatherapps.Classes.Place
import com.example.weatherapps.R
import com.example.weatherapps.ViewModels.MapViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen(mapApi: MapApi,mapView: MapView,
              onClose: () -> Unit,
              onPlaceSelected: (Place) -> Unit,
              navController: NavHostController) {
    // Use the mapApi to interact with the maps functionality and display the map
    // ...
    var isPlaceSelected by remember { mutableStateOf(false) }
    var selectedLatitude by remember { mutableStateOf(0.0) }
    var selectedLongitude by remember { mutableStateOf(0.0) }
    var selectedPlaceName by remember { mutableStateOf("") }

    val mapView = rememberMapViewWithLifecycle(LocalLifecycleOwner.current)

    LaunchedEffect(Unit) {
        mapApi.displayMap(mapView) { latitude, longitude, placeName ->
            // Update the state values for place selection

            isPlaceSelected = true
            selectedLatitude = latitude
            selectedLongitude = longitude
            selectedPlaceName = placeName
            //Log.e("Details of place: ","${latitude}")

            // Create a Place instance with the selected place details
            val selectedPlace = Place(latitude, longitude, placeName)

            // Invoke the onPlaceSelected callback to pass the selected place to the caller
            onPlaceSelected(selectedPlace)

            // Move the marker to the selected place
            mapApi.moveMarkerToPlace(selectedPlace, mapView)

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Display the map using the mapApi
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        // Close icon
        Icon(
            Icons.Filled.Close,
            contentDescription = "Close",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {
                   try {
                       //onClose.invoke()
                       navController.popBackStack()
                   }catch (e:Exception){
                       e.printStackTrace()
                   }

                    // Handle close icon click
                }
        )

        // "Get Weather" button
        Button(
            onClick = {
                // Handle "Get Weather" button click
            },
            enabled = isPlaceSelected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Get Weather")
        }
    }


}



@Composable
fun rememberMapViewWithLifecycle(
    lifecycleOwner: LifecycleOwner
): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            Log.d("MapView", "MapView created")
        }
    }

    DisposableEffect(Unit) {
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                mapView.onCreate(Bundle())
            }

            override fun onStart(owner: LifecycleOwner) {
                mapView.onStart()
            }

            override fun onResume(owner: LifecycleOwner) {
                mapView.onResume()
            }

            override fun onPause(owner: LifecycleOwner) {
                mapView.onPause()
            }

            override fun onStop(owner: LifecycleOwner) {
                mapView.onStop()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                mapView.onDestroy()
            }
        }


        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            mapView.onDestroy()
            Log.d("MapView", "Mapview disposed")
        }
        }


    return mapView
}






/*@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            Log.d("MapView", "MapView created")
            onCreate(Bundle())
            onResume()
        }
    }



    /*val lifecycleObserver = remember {
        object : DefaultLifecycleObserver {
            /*override fun onCreate(owner: LifecycleOwner) {
                mapView.onCreate(null)
            }*/

            override fun onStart(owner: LifecycleOwner) {
                mapView.onStart()
            }

            override fun onResume(owner: LifecycleOwner) {
                mapView.onResume()
            }

            override fun onPause(owner: LifecycleOwner) {
                mapView.onPause()
            }

            override fun onStop(owner: LifecycleOwner) {
                mapView.onStop()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                mapView.onDestroy()
            }
        }
    }*/

    /*DisposableEffect(Unit) {
        onDispose {
            mapView.onDestroy()
        }
    }*/

    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleObserver = remember {
        object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                mapView.onDestroy()
            }
        }
    }


    DisposableEffect(lifecycleOwner, mapView) {
        val observer = lifecycleObserver
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            mapView.onDestroy()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }




    /*val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }*/

    DisposableEffect(Unit) {
        onDispose {
            mapView.onDestroy()
        }
    }


    return mapView
}*/

/*
@SuppressLint("MissingPermission")
@Composable
fun MapScreen(onLocationSelected: (LatLng) -> Unit) {
    val context = LocalContext.current
    val mapViewRef = remember { Ref<MapView>() }
    val mapReadyState = remember { mutableStateOf(false) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    DisposableEffect(Unit) {
        val mapView = mapViewRef.value

        mapView?.onCreate(Bundle())
        mapView?.onResume()

        val mapCallback = object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Set up map settings and listeners
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true

                // Handle map click event
                googleMap.setOnMapClickListener { latLng ->
                    onLocationSelected(latLng)
                }

                // Handle location button click event
                googleMap.setOnMyLocationButtonClickListener {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            val latLng = LatLng(location.latitude, location.longitude)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }
                    }
                    true
                }

                // Map is now ready
                mapReadyState.value = true
            }
        }

        mapView?.getMapAsync(mapCallback)

        onDispose {
            mapView?.onPause()
            mapView?.onDestroy()
        }
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                //id = R.id.map_view
                mapViewRef.value = this
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}*/


