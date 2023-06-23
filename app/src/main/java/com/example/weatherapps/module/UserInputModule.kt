package com.example.weatherapps.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserInputModule {
    @Provides
    fun provideAddress(): String = ""
}
