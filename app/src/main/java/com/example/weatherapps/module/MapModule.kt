package com.example.weatherapps.module

//import com.example.weatherapps.ui.Weather.MapView
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.node.Ref
import com.example.weatherapps.ui.Weather.MapScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
object MapModule {
    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }


    @Provides
    fun provideOnMapReadyCallback( fusedLocationClient: FusedLocationProviderClient,
                                   onLocationSelected: (LatLng) -> Unit): OnMapReadyCallback {
        return object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Set up map settings and listeners
                //googleMap.isMyLocationEnabled = true // To add permission check//
                googleMap.uiSettings.isMyLocationButtonEnabled = true

                // Handle map click event
                googleMap.setOnMapClickListener { latLng ->
                    onLocationSelected(latLng)
                }

                // Handle location button click event
                googleMap.setOnMyLocationButtonClickListener {
                    /*fusedLocationClient.lastLocation.addOnSuccessListener //to add permission check//
                   { location ->
                        location?.let {
                            val latLng = LatLng(location.latitude, location.longitude)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }
                    }*/
                    true
                }
            }
        }
    }

    @Provides
    fun provideMapView(context: Context, mapViewRef: Ref<MapView>): MapView {
        val mapView = MapView(context)
        mapViewRef.value = mapView
        return mapView
    }
}
