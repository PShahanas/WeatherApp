package com.example.weatherapps.Repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.weatherapps.API.APIinterface
//import com.example.weatherapps.API.ApiUtilities
//import com.example.weatherapps.API.ApiUtilities
//import com.example.weatherapps.API.ApiUtilities
//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.Model.toWeatherInfo
import com.example.weatherapps.ui.Weather.WeatherInfo
import com.example.weatherapps.utils.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl  @Inject constructor (private val api:APIinterface) : WeatherRepository{
    override suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo> {
        return try{

            Log.e("Response", api.getWeatherData(lat = lat, long = lon).weatherData.toString())

           Resource.Success(

               data = api.getWeatherData(
                   lat = lat,
                   long = lon
               ).toWeatherInfo()
           )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}