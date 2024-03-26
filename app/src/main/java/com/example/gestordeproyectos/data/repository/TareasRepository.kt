package com.example.gestordeproyectos.data.repository

import android.util.Log
import com.example.gestordeproyectos.data.GestorApi
import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.TareasDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TareasRepository @Inject constructor(
    private val api: GestorApi
) {
    fun getTareas(): Flow<Resource<List<TareasDto>>> = flow {
        try {
            emit(Resource.Loading())

            val tareas = api.getTareas()

            emit(Resource.Success(tareas))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    fun getTareaById(id: Int): Flow<Resource<TareasDto>> = flow {
        try {
            emit(Resource.Loading())

            val tarea =
                api.getTareaById(id)

            emit(Resource.Success(tarea))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    fun getParticipantesTarea(id: Int): Flow<Resource<List<UsuariosDto>>> = flow {
        try {
            emit(Resource.Loading())

            val usuarios = api.getParticipantesTarea(id)

            emit(Resource.Success(usuarios))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    fun getTareasByProyectoId(id: Int): Flow<Resource<List<TareasDto>>> = flow {
        try {
            emit(Resource.Loading())

            val tareas = api.getTareasByProyectoId(id)

            emit(Resource.Success(tareas))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    fun getTareasByIdUsuario(usuarioId: Int, proyectoId: Int): Flow<Resource<List<TareasDto>>> = flow {
        try {
            emit(Resource.Loading())

            val tareas = api.getTareasByIdUsuario(usuarioId, proyectoId)

            emit(Resource.Success(tareas))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
    suspend fun postTareas(tarea: TareasDto) = api.postTareas(tarea)
    suspend fun putTarea(id:Int, tarea: TareasDto) {
        api.putTarea(id, tarea)
    }
    suspend fun deleteTareas(id: Int) : TareasDto? {
        return api.deleteTareas(id).body()
    }
}

