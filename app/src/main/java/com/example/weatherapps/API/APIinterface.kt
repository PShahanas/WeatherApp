package com.example.weatherapps.API

import com.example.weatherapps.Model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIinterface {

  @GET("weather")
   fun getCurrentWeather(
      @Query("lat") lat: String,
      @Query("lon") lon: String,
      @Query("appID") appID:String
  ):Call<WeatherModel>

  @GET("weather")
   fun getCityWeather(
      @Query("city") city:String,
      @Query("appID") appID:String
  ):Call<WeatherModel>


}