package com.example.gestordeproyectos.data.repository

import com.example.gestordeproyectos.data.UsuarioApi
import com.example.gestordeproyectos.data.dto.LoginRequest
import com.example.gestordeproyectos.data.dto.RegisterRequest
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsuariosRepository @Inject constructor(
    private val api: UsuarioApi
) {
    fun getUsuarios(): Flow<Resource<List<UsuariosDto>>> = flow {
        try {
            emit(Resource.Loading())

            val usuario = api.getUsuarios()

            emit(Resource.Success(usuario))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }


    suspend fun getUsuarioById(id: Int): UsuariosDto? {
        return api.getUsuarioById(id)
    }
    suspend fun postLogin(loginRequest: LoginRequest) = api.postLogin(loginRequest)
    suspend fun postRegister(registerRequest: RegisterRequest) = api.postRegister(registerRequest)
    suspend fun postUsuario(usuario: UsuariosDto) = api.postUsuario(usuario)
    suspend fun putUsuario(id:Int, usuario: UsuariosDto) = api.putUsuario(id, usuario)
}