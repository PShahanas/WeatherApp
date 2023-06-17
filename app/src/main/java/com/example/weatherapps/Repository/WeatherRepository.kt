package com.example.weatherapps.Repository

import androidx.lifecycle.MutableLiveData
//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.ui.Weather.WeatherInfo
import com.example.weatherapps.utils.Resource
import retrofit2.Response

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, lon : Double) : Resource<WeatherInfo>

}