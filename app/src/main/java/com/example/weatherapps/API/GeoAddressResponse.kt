package com.example.weatherapps.API

import com.google.gson.annotations.SerializedName

data class GeoAddressResponse(
    @SerializedName("results")
    val results: List<Place>,
    // Add other necessary properties
)

data class Place(
    @SerializedName("formatted_address")
    val formattedAddress: String,
    // Add other necessary properties
)