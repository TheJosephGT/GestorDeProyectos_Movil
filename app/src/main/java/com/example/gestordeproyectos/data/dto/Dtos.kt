package com.example.gestordeproyectos.data.dto

import androidx.room.Relation
data class RegisterRequest(
    val nickName: String = "",
    val nombreCompleto: String = "",
    val correo: String = "",
    val clave: String = "",
)
data class UsuariosDto(
    val usuarioId: Int? = null,
    val nickName: String = "",
    val nombreCompleto: String = "",
    val correo: String = "",
    val clave: String = "",
    val activo: Boolean = true,
    @Relation(parentColumn = "usuarioId", entityColumn = "usuarioId")
    val usuarioProyectos: List<UsuarioProyectos> = listOf()
)
data class UsuarioProyectos(
    val usuarioProyectoId: Int? = null,
    val proyectoId: Int? = null,
    val usuarioId: Int? = null,
    val rol: String = "",
    val activo: Boolean = true,
    @Relation(parentColumn = "usuarioProyectoId", entityColumn = "usuarioProyectoId")
    val usuarioTareas: List<UsuarioTareas> = listOf()
)
data class UsuarioTareas(
    val usuarioTareasId: Int? = null,
    val tareaId: String = "",
    val usuarioId: String = "",
    val activo: Boolean = true
)


// -----------------

data class Tareas(
    val tareaId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val fechaCreacion: String = "",
    val fechaVencimiento: String = "",
    val prioridad: String = "",
    val activo: Boolean = true
)
data class TareasProyecto(
    val tareasProyectoId: Int? = null,
    val proyectoId: Int? = null,
    val tareaId: Int? = null
)

data class Proyectos(
    val proyectoId: Int? = null,
    val titulo: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val fechaCreacion: String = "",
    val progreso: Double,
    val creador: Int? = null,
    val activo: Boolean = true,
    @Relation(parentColumn = "proyectoId", entityColumn = "proyectoId")
    val usuarioProyectos: List<UsuarioProyectos> = listOf(),
    @Relation(parentColumn = "proyectoId", entityColumn = "proyectoId")
    val tareasProyecto: List<TareasProyecto> = listOf()
)
