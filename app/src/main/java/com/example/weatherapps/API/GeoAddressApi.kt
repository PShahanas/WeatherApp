package com.example.weatherapps.API

import com.example.weatherapps.Classes.UserInput
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoAddressApi {
    @GET("geocode/json")
    suspend fun searchPlaces(
        @Query("address") query: UserInput,
        @Query("key") apiKey: String
    ): Response<GeoAddressResponse>
}