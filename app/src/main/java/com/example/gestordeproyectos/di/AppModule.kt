package com.example.gestordeproyectos.di

import com.example.gestordeproyectos.data.GestorApi
import com.example.gestordeproyectos.data.repository.ProyectosRepository
import com.example.gestordeproyectos.data.repository.TareasRepository
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
    fun providesUsuariosApi(moshi: Moshi): GestorApi {
        return Retrofit.Builder()
            .baseUrl("http://www.gestortareasapi.somee.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GestorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuariosRepository(api: GestorApi): UsuariosRepository {
        return UsuariosRepository(api)
    }

    @Provides
    @Singleton
    fun provideProyectosRepository(api: GestorApi): ProyectosRepository {
        return ProyectosRepository(api)
    }

    @Provides
    @Singleton
    fun provideTareasRepository(api: GestorApi): TareasRepository {
        return TareasRepository(api)
    }

}