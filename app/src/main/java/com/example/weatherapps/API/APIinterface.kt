package com.example.weatherapps.API

//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.utils.Resource
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIinterface {

  /*@GET("weather")
   fun getCurrentWeather(
      @Query("lat") lat: String,
      @Query("lon") lon: String,
      @Query("appID") appID:String
  ):Call<WeatherModel>

  @GET("weather")
   fun getCityWeather(
      @Query("city") city:String,
      @Query("appID") appID:String
  ):Call<WeatherModel>*/

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): ResponseDto


}