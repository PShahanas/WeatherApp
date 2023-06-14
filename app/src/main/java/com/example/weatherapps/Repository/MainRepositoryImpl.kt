package com.example.weatherapps.Repository

//import com.example.weatherapps.API.ApiHelper
import com.example.weatherapps.Location.LocationService
import javax.inject.Inject

/*class MainRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) {

    val currentLat = LocationService.currentLatitude
    val currentLon = LocationService.currentLongitude
    private val apiKey = "f70ca239bf30695349b25a9bb3361c69"

    suspend fun getWeather() = currentLat?.let {
        if (currentLon != null) {
            apiHelper.getCurrentWeather(
                lat = it,
                lon = currentLon, apiKey)
        }
    }

}*/