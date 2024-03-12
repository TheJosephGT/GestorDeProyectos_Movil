package com.example.gestordeproyectos.data.remote.dto

import com.example.gestordeproyectos.ui.theme.remote.dto.LoginRequest
import com.example.gestordeproyectos.ui.theme.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/Usuarios/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Unit>

    @POST("api/Usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Unit>
}