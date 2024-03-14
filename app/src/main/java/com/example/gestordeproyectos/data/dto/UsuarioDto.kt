package com.example.gestordeproyectos.data.dto

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class UsuariosDto(
    val usuarioId: Int? = null,
    val nickName: String = "",
    val nombreCompleto: String = "",
    val correo: String = "",
    val clave: String = "",
    val activo: Boolean = true
)
data class RegisterRequest(
    val nickName: String = "",
    val nombreCompleto: String = "",
    val correo: String = "",
    val clave: String = "",
)

data class LoginRequest(
    @field:Json(name = "Correo") val correo: String,
    @field:Json(name = "Clave") val clave: String
)