package com.example.gestordeproyectos.data.repository

import com.example.gestordeproyectos.data.GestorApi
import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProyectosRepository @Inject constructor(
    private val api: GestorApi
) {
    fun getProyectos(): Flow<Resource<List<ProyectosDto>>> = flow {
        try {
            emit(Resource.Loading())

            val proyecto = api.getProyectos()

            emit(Resource.Success(proyecto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    fun getProyectoId(id: Int): Flow<Resource<ProyectosDto>> = flow {
        try {
            emit(Resource.Loading())

            val proyecto =
                api.getProyectoById(id)

            emit(Resource.Success(proyecto))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    suspend fun postProyecto(proyecto: ProyectosDto) = api.postProyectos(proyecto)
    suspend fun putProyecto(id:Int, proyecto: ProyectosDto) {
        api.putProyecto(id, proyecto)
    }
}