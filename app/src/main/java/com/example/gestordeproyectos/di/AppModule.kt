package com.example.gestordeproyectos.di

import android.content.Context
import androidx.room.Room
import com.example.gestordeproyectos.data.remote.dto.LoginApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    @Singleton
    @Provides
    fun providesGestorApi(moshi: Moshi): LoginApi {
        return Retrofit.Builder().baseUrl("https://localhost:7145/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
            .create(LoginApi::class.java)
    }

}