package com.example.gestordeproyectos.data.repository

import com.example.gestordeproyectos.data.GestorApi
import com.example.gestordeproyectos.data.dto.UsuarioProyectosDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsuarioProyectos @Inject constructor(
    private val api: GestorApi
) {
    fun getUsuarioProyectos(): Flow<Resource<List<UsuarioProyectosDto>>> = flow {
        try {
            emit(Resource.Loading())

            val usuarioProyectos = api.getUsuarioProyectos()

            emit(Resource.Success(usuarioProyectos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    fun getUsuarioProyectosById(id: Int): Flow<Resource<UsuarioProyectosDto>> = flow {
        try {
            emit(Resource.Loading())

            val usuarioProyecto =
                api.getUsuarioProyectosById(id)

            emit(Resource.Success(usuarioProyecto))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    suspend fun postUsuarioProyectos(usuarioProyecto: UsuarioProyectosDto) = api.postUsuarioProyectos(usuarioProyecto)
    suspend fun putUsuarioProyectos(id:Int, usuarioProyecto: UsuarioProyectosDto) {
        api.putUsuarioProyectos(id, usuarioProyecto)
    }
}