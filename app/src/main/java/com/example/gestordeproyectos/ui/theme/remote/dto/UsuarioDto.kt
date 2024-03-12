package com.example.gestordeproyectos.ui.theme.remote.dto
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

data class LoginRequest(
    @field:Json(name = "correo") val correo: String,
    @field:Json(name = "clave") val clave: String
)

data class RegisterRequest(
    @field:Json(name = "nickName") val nickName: String,
    @field:Json(name = "nombreCompleto") val nombreCompleto: String,
    @field:Json(name = "correo") val correo: String,
    @field:Json(name = "clave") val clave: String
)
