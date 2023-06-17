package com.example.weatherapps.API

import com.example.weatherapps.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory

/*object ApiUtilities {

    private var retrofit:Retrofit?=null

    var BASE_URL = "https://api.openweathermap.org/data/2.5/"
    // var BASE_URL = "https://api.open-meteo.com/"

    /*fun getApiInterface(): APIinterface?{

        if (retrofit ==null){

            retrofit =Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        }

        return retrofit?.create(APIinterface::class.java)

    }*/

    val retrofitClient: Retrofit.Builder by lazy {

        val levelType: HttpLoggingInterceptor.Level
        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            levelType = HttpLoggingInterceptor.Level.BODY else levelType = HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface:  APIinterface by lazy {
        retrofitClient
            .build()
            .create(APIinterface::class.java)
    }



}*/