package com.example.gestordeproyectos.data.dto

import androidx.lifecycle.MutableLiveData

data class UsuariosDto(
    val usuarioId: Int? = null,
    var nickName: String = "",
    val nombreCompleto: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "",
    val activo: Boolean = true,
)
data class ParticipantesProyectosDTO(
    val participanteProyectoId: Int? = null,
    val proyectoId: Int? = null,
    val usuarioId: Int? = null,
    val activo: Boolean = true,
)
data class ParticipantesTareasDTO(
    val articipanteTareaId: Int? = null,
    val tareaId: String = "",
    val usuarioId: String = "",
    val activo: Boolean = true
)


// -----------------

data class TareasDto(
    val tareaId: Int? = null,
    val proyectoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val prioridad: String = "",
    val activo: Boolean = true,
    val participantes: List<ParticipantesTareasDTO> = emptyList()
)

data class ProyectosDto(
    val proyectoId: Int? = null,
    val titulo: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val fechaCreacion: String = "",
    val progreso: Int? = null,
    val activo: Boolean = true,
    val participantes:  MutableList<ParticipantesProyectosDTO> = mutableListOf()
)
