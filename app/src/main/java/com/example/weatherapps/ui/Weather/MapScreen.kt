package com.example.weatherapps.ui.Weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.weatherapps.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

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
}


