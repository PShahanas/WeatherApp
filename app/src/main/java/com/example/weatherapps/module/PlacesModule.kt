package com.example.weatherapps.module

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/*@Module
@InstallIn(SingletonComponent::class)
object PlacesModule {

    @Provides
    fun providePlacesClient(application: Application): PlacesClient {
        val apiKey = "AIzaSyA0DVBPdLQrhafLeoeU6KDl1X6qZ9COiUE"
        Places.initialize(application, apiKey)
        return Places.createClient(application)
    }
}*/
