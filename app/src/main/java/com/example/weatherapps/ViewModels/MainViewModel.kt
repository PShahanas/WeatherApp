package com.example.weatherapps.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapps.Location.LocationService
//import com.example.weatherapps.Repository.WeatherRepository
//import com.example.weatherapps.Repository.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*@HiltViewModel
class MainViewModel @Inject constructor(
    //private val weatherRepository: WeatherRepository
    private val mainRepository: WeatherRepository
) : ViewModel()  {

    var weatherData : MutableLiveData<Unit>? = null

    suspend fun getWeatherInViewmodel(){

        val currentLat = LocationService.currentLatitude
        val currentLon = LocationService.currentLongitude

        weatherData?.value = currentLat?.let {
            if (currentLon != null) {
                mainRepository.getWeatherData(lat = currentLat,
                    lon = currentLon )
            }
        }
    }*/


/*
    private val weatherDataEmitter = MutableLiveData<WeatherModel>()

    val weatherData : MutableLiveData<WeatherModel> = weatherDataEmitter
        init {
            loadWeatherData()
        }

    private fun loadWeatherData() {
        //weatherDataEmitter.value = weatherRepository.getWeatherData(lat = , lon = )
    }

*/
//}