package com.example.weatherapps.API

//import com.example.weatherapps.Model.WeatherModel
import com.example.weatherapps.utils.Resource
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIinterface {


}

interface FirstAPIImplementation : APIinterface {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): ResponseDto
}
    /*@GET("search")
    suspend fun searchPlaces(@Query("query") query: String): Response<PlacesResponse>

    @GET("geocode")
    suspend fun getCoordinates(@Query("place") place: String): Response<CoordinatesResponse>*/



/*interface SecondAPIImplementation : APIinterface{

    @GET("search")
    suspend fun searchAddresses(
        @Query("text") query: String,
        @Query("apiKey") apiKey: String
    ): Response<List<Address>>


}*/