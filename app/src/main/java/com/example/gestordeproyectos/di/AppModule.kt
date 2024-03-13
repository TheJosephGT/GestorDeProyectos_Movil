package com.example.gestordeproyectos.di

import com.example.gestordeproyectos.data.UsuarioApi
import com.example.gestordeproyectos.data.repository.UsuariosRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providesUsuariosApi(moshi: Moshi): UsuarioApi {
        return Retrofit.Builder()
            .baseUrl("http://www.gestorapi.somee.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsuarioApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuariosRepository(usuarioApi: UsuarioApi): UsuariosRepository {
        return UsuariosRepository(usuarioApi)
    }

}