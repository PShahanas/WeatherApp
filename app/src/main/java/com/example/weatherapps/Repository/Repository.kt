package com.example.weatherapps.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
//import com.example.weatherapps.API.ApiUtilities
import com.example.weatherapps.Location.LocationService
//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.ui.Weather.WeatherData
import com.example.weatherapps.ui.Weather.WeatherInfo
import com.example.weatherapps.utils.Resource
//import kotlinx.coroutines.flow.internal.NopCollector.emit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException

/*object Repository {

    val latitude = LocationService.currentLatitude
    val longitude = LocationService.currentLongitude
    val apiKey = "f70ca239bf30695349b25a9bb3361c69"


    val serviceSetterGetter = MutableLiveData<WeatherModel>()

    fun getServicesApiCall(): MutableLiveData<WeatherModel> {

        val call = ApiUtilities.apiInterface.getCurrentWeather(latitude.toString(), longitude.toString(), apiKey)

        call.enqueue(object: Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

          override  fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
              serviceSetterGetter.value = response.body()
               Resource.Success<WeatherInfo>(
                   data = ApiUtilities.apiInterface.getCurrentWeather(
                       latitude.toString(), longitude.toString(), apiKey
                   ))

                }
                // TODO("Not yet implemented")
                //Log.v("DEBUG : ", response.body().toString())

                //val data = response.body()

               // serviceSetterGetter.value = response.body()



        })

       return serviceSetterGetter


    }

    /*fun getReport() = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = oaRepository.getWeatherReport()
            if (apiResponse.isSuccessful) {
                val result = apiResponse.body() as OpenApiData
                emit(Resource.Success(result.toWeatherData()))
            } else {
                emit(Resource.Error("Api is unsuccessful"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }*/

    /*suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }*/

}*/