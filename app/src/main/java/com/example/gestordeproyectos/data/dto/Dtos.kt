package com.example.gestordeproyectos.data.dto
data class UsuariosDto(
    val usuarioId: Int? = null,
    var nickName: String = "",
    val nombreCompleto: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "",
    val activo: Boolean = true,
)
data class UsuarioProyectosDto(
    val usuarioProyectoId: Int? = null,
    val proyectoId: Int? = null,
    val usuarioId: Int? = null,
    val activo: Boolean = true,
)
data class UsuarioTareasDto(
    val usuarioTareasId: Int? = null,
    val tareaId: String = "",
    val usuarioId: String = "",
    val activo: Boolean = true
)


// -----------------

data class TareasDto(
    val tareaId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val fechaCreacion: String = "",
    val fechaVencimiento: String = "",
    val prioridad: String = "",
    val activo: Boolean = true
)
data class TareasProyectoDto(
    val tareasProyectoId: Int? = null,
    val proyectoId: Int? = null,
    val tareaId: Int? = null
)

data class ProyectosDto(
    val proyectoId: Int? = null,
    val titulo: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val fechaCreacion: String = "",
    val progreso: Double,
    val activo: Boolean = true,
)
