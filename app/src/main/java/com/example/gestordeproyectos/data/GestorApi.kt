package com.example.gestordeproyectos.data


import com.example.gestordeproyectos.data.dto.UsuariosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GestorApi {
    @GET("api/Usuarios")
    suspend fun getUsuarios():List<UsuariosDto>
    @GET("api/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): UsuariosDto?
    @POST("api/Usuarios")
    suspend fun postUsuarios(@Body usuario: UsuariosDto): Response<UsuariosDto>
}