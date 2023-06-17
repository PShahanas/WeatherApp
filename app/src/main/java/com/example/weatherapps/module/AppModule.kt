package com.example.weatherapps.module

import android.app.Application
import com.example.weatherapps.API.APIinterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
//import com.example.weatherapps.Repository.WeatherRepository
//import com.example.weatherapps.Repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): APIinterface {
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