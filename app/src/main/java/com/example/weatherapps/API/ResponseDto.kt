package com.example.weatherapps.API

import com.squareup.moshi.Json

data class ResponseDto(
    @field:Json(name = "hourly")
    val weatherData: RequiredWeatherData
)
