package com.example.gestordeproyectos.data


import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.TareasDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GestorApi {
    //Usuarios
    @GET("api/Usuarios")
    suspend fun getUsuarios():List<UsuariosDto>
    @GET("api/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): UsuariosDto
    @POST("api/Usuarios")
    suspend fun postUsuarios(@Body usuario: UsuariosDto): Response<UsuariosDto>
    @PUT("/api/Usuarios/{id}")
    suspend fun putUsuario(@Path("id") id:Int, @Body usuario: UsuariosDto): Response<Unit>

    //Proyectos:
    @GET("api/Proyectos")
    suspend fun getProyectos():List<ProyectosDto>
    @GET("api/Proyectos/{id}")
    suspend fun getProyectoById(@Path("id") id: Int): ProyectosDto
    @GET("api/Proyectos/participantesProyecto/{id}")
    suspend fun getParticipantesProyecto(@Path("id") id: Int):List<UsuariosDto>
    @POST("api/Proyectos")
    suspend fun postProyectos(@Body proyecto: ProyectosDto): Response<ProyectosDto>
    @PUT("/api/Proyectos/{id}")
    suspend fun putProyecto(@Path("id") id:Int, @Body proyecto: ProyectosDto): Response<Unit>
    @DELETE("api/Proyectos/{id}")
    suspend fun deleteProyectos(@Path("id") id: Int): Response<ProyectosDto>

    //Tareas:
    @GET("api/Tareas")
    suspend fun getTareas():List<TareasDto>
    @GET("api/Tareas/{id}")
    suspend fun getTareaById(@Path("id") id: Int): TareasDto
    @GET("api/Tareas/participantesTarea/{id}")
    suspend fun getParticipantesTarea(@Path("id") id: Int):List<UsuariosDto>
    @GET("api/Tareas/TareasPorIdProyecto/{id}")
    suspend fun getTareasByProyectoId(@Path("id") id: Int):List<TareasDto>
    @POST("api/Tareas")
    suspend fun postTareas(@Body tarea: TareasDto): Response<TareasDto>
    @PUT("/api/Tareas/{id}")
    suspend fun putTarea(@Path("id") id:Int, @Body tarea: TareasDto): Response<Unit>
    @DELETE("api/Tareas/{id}")
    suspend fun deleteTareas(@Path("id") id: Int): Response<TareasDto>
}