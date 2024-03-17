package com.example.gestordeproyectos.data.repository

import com.example.gestordeproyectos.data.GestorApi
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsuariosRepository @Inject constructor(
    private val api: GestorApi
) {
    fun getUsuarios(): Flow<Resource<List<UsuariosDto>>> = flow {
        println("Pasando por ususario:")
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


    fun getUsuarioId(id: Int): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())

            val usuario =
                api.getUsuarioById(id)

            emit(Resource.Success(usuario))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    suspend fun postUsuarios(usuario: UsuariosDto) = api.postUsuarios(usuario)
    suspend fun putUsuario(id:Int, usuario: UsuariosDto) {
        api.putUsuario(id, usuario)
    }
}