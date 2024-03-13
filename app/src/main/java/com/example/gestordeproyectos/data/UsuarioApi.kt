package com.example.gestordeproyectos.data

import com.example.gestordeproyectos.data.dto.LoginRequest
import com.example.gestordeproyectos.data.dto.RegisterRequest
import com.example.gestordeproyectos.data.dto.UsuariosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {
    @GET("api/Usuarios")
    suspend fun getUsuarios():List<UsuariosDto>
    @GET("api/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): UsuariosDto?
    @POST("api/Usuarios/register")
    suspend fun postRegister(@Body registerRequest: RegisterRequest): Response<RegisterRequest>
    @PUT("api/Usuarios/{id}")
    suspend fun putUsuario(@Path("id") id:Int, @Body usuario: UsuariosDto): Response<Unit>

    @POST("api/Usuarios/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<LoginRequest>
}