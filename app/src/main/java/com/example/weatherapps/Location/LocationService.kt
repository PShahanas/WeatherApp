package com.example.weatherapps.Location

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import com.example.weatherapps.API.MapApi
import com.example.weatherapps.ViewModels.weatherViewModel
import com.example.weatherapps.ui.Weather.rememberMapViewWithLifecycle
import com.example.weatherapps.ui.mainActivity
//import com.example.weatherapps.mainActivity
//import com.example.weatherapps.ui.mainActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class LocationService : Service(){

   companion object{
       var currentLatitude: Double? = null
       var currentLongitude: Double? = null

   }


    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null

    private var location: Location?=null

    override fun onCreate() {
        super.onCreate()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).setIntervalMillis(500).build()
        locationCallback = object :LocationCallback(){
            override fun onLocationAvailability(p0: LocationAvailability) {
                super.onLocationAvailability(p0)
            }

            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                onNewLocation(locationResult)
                for(location in locationResult.locations){

                    currentLatitude = location.latitude
                    currentLongitude = location.longitude

                   // val mapView = MapView(applicationContext)
// obtain reference to your MapView
                   // val updatedLatLng = LatLng(currentLatitude!!, currentLongitude!!)
                    //mapApi.updateMarkerPosition(updatedLatLng, mapView)

                    mainActivity.let{
                        if (it != null) {
                            it.getLocation(locationResult)
                            //it.fetchCurrentLocationWeather(currentLatitude, currentLongitude)
                        }

                    }
                    weatherViewModel.let{
                        if (it != null) {
                            it.getLocation(locationResult)
                            //it.fetchCurrentLocationWeather(currentLatitude, currentLongitude)
                        }

                    }




                }

            }
        }

    }

    fun createLocationRequest(){
      try {
          if (checkPermission()) {
              fusedLocationProviderClient?.requestLocationUpdates(
                  locationRequest!!, locationCallback!!, null
              )
          }
      }
          catch (e:java.lang.Exception){
              e.printStackTrace()
          }

    }

    fun onNewLocation(locationResult: LocationResult){
        location = locationResult.lastLocation
    }

    private fun removeLocationUpdates(){
        locationCallback?.let {
            fusedLocationProviderClient?.removeLocationUpdates(it)
        }
        stopSelf()
    }

    fun checkPermission(): Boolean{
        return (ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        //Toast.makeText(this,"ENTERED SERVICE", Toast.LENGTH_LONG).show()
       // Log.i("ENTERED SERVICE", true.toString())
        createLocationRequest()

        return START_STICKY


    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        removeLocationUpdates()
    }



}