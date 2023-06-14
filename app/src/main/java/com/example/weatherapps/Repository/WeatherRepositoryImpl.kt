package com.example.weatherapps.Repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.weatherapps.API.APIinterface
import com.example.weatherapps.API.ApiUtilities
//import com.example.weatherapps.API.ApiUtilities
//import com.example.weatherapps.API.ApiUtilities
import com.example.weatherapps.Model.WeatherModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/*class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {

    private val apiKey = "f70ca239bf30695349b25a9bb3361c69"

    override suspend fun getWeatherData(lat: Double, lon: Double): WeatherModel? {

        val serviceSetterGetter = MutableLiveData<WeatherModel>()

        val call = ApiUtilities.getApiInterface()?.getCurrentWeather(lat, lon, apiKey)

        if (call != null) {
            call.enqueue(object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    //val data = response.body()

                    serviceSetterGetter.value = response.body()
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {

                    Log.e("API CALL", "Error")

                }

            })

        }

        return serviceSetterGetter.value
    }
}







        /*  try {
            ApiUtilities.getApiInterface()?.getCurrentWeather(lat, lon, appID = apiKey )?.enqueue(
                object : Callback<WeatherModel> {
                    override fun onResponse(
                        call: Call<WeatherModel>,
                        response: Response<WeatherModel>
                    ) {
                        GlobalScope.launch {
                            val data = api.getCurrentWeather(lat, lon, apiKey)
                        }
                    }

                    override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                        //TODO("Not yet implemented")
                    }

                }
            )
        }catch (e: Exception){
              e.printStackTrace()
              Log.e("API Calling", "ERROR")
        }*/





*/