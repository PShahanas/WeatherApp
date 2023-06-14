package com.example.weatherapps.module

import com.example.weatherapps.API.APIinterface
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