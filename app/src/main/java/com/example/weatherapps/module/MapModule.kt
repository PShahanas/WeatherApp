package com.example.weatherapps.module

//import com.example.weatherapps.ui.Weather.MapView
import android.app.Application
import android.content.Context
import com.example.weatherapps.API.MapApi
import com.example.weatherapps.API.MapApiImpl
import com.example.weatherapps.ViewModels.MapViewModel
import com.example.weatherapps.ViewModels.SearchViewModel
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
object MapsModule {

    @Provides
    @Singleton
    fun provideMapApi(context: Context, @Named("mapsApiKey") apiKey: String): MapApi {
        return MapApiImpl(context, apiKey)
    }

    @Provides
    @Singleton
    @Named("mapsApiKey")
    fun provideMapsApiKey(): String {
        return "AIzaSyDj-LtQBmCWZBGiBrOHeGBjZsXRYdeMkfI"
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideMapViewModel(mapApi: MapApi): MapViewModel{
        return MapViewModel(mapApi)
    }


    @Provides
    @Singleton
    fun provideMapFragment(): SupportMapFragment {
        return SupportMapFragment.newInstance()
    }



}



/*
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
}*/
