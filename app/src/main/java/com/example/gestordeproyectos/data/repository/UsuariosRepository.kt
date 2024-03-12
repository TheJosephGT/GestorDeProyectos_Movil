package com.example.gestordeproyectos.data.repository

import com.example.gestordeproyectos.data.dto.RegisterRequest
import com.example.gestordeproyectos.data.remote.dto.LoginApi
import com.example.gestordeproyectos.ui.theme.remote.dto.LoginRequest
import com.example.gestordeproyectos.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginApi
) {
    suspend fun login(loginRequest: LoginRequest): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.login(loginRequest)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error al iniciar sesión"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error("Verificar tu conexión a internet"))
        }
    }

}