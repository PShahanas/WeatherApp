package com.example.weatherapps.module

import com.example.weatherapps.API.GeoAddressApi
import com.example.weatherapps.Classes.UserInput
//import com.example.weatherapps.Repository.GeoAddressRepositoryImpl
//import com.example.weatherapps.Repository.GeoaddressRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoAddressApiModule {


    @Provides
    fun provideGeoAddressApi(userInput: UserInput): GeoAddressApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.geoaddress.io/v1/?address=${userInput.address}&key=AIzaSyA0DVBPdLQrhafLeoeU6KDl1X6qZ9COiUE") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        return retrofit.create(GeoAddressApi::class.java)
    }

    @Provides
    fun provideUserInput(input: String): UserInput {
        return UserInput(input)
    }


}