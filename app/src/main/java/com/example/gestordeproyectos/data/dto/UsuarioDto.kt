package com.example.gestordeproyectos.data.dto

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class UsuarioDto(
    @PrimaryKey
    var usuarioId: Int? = null,
    var nickName: String = "",
    var nombreCompleto: String = "",
    var correo: String = "",
    var clave: String = "",
    var activo: Boolean = true
)
data class RegisterRequest(
    @field:Json(name = "NickName") val nickName: String,
    @field:Json(name = "NombreCompleto") val nombreCompleto: String,
    @field:Json(name = "Correo") val correo: String,
    @field:Json(name = "Clave") val clave: String
)

data class LoginRequest(
    @field:Json(name = "Correo") val correo: String,
    @field:Json(name = "Clave") val clave: String
)