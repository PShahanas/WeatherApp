package com.example.weatherapps.module

import android.app.Application
import com.example.weatherapps.API.APIinterface
import com.example.weatherapps.API.FirstAPIImplementation
import com.example.weatherapps.API.GeoAddressApi
import com.example.weatherapps.ViewModels.SearchViewModel
//import com.example.weatherapps.Repository.GeocodingRepository
//import com.example.weatherapps.Repository.PlacesRepository
//import com.example.weatherapps.API.SecondAPIImplementation
//import com.example.weatherapps.Repository.GeoaddressRepository
//import com.example.weatherapps.Repository.GeoaddressRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
//import com.example.weatherapps.Repository.WeatherRepository
//import com.example.weatherapps.Repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): FirstAPIImplementation {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }



    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }


    @Provides
    fun providePlacesClient(application: Application): PlacesClient {
        // Initialize and return your PlacesClient instance here

        // Initialize Places SDK
        Places.initialize(application, "AIzaSyDJb9Feg4ntB_PpGEwdEAuy3_ZMY83EPbU")

        // Create and return PlacesClient instance
        return Places.createClient(application)
    }

    @Provides
    fun provideSearchViewModel(placesClient: PlacesClient): SearchViewModel {
        return SearchViewModel(placesClient)
    }

}






/*@Module
@InstallIn(AppModule ::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): APIinterface {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }


}*/
/*@InstallIn(SingletonComponent::class)
object AppModule {

     @Provides
     @Singleton
     fun provideWeatherRepository(): WeatherRepository = WeatherRepositoryImpl()


    fun provideAPIinterface() : APIinterface{
          return Retrofit.Builder()
               .baseUrl("https://api.openweathermap.org/data/2.5/")
               .addConverterFactory(MoshiConverterFactory.create())
               .build()
               .create()
     }


}*/