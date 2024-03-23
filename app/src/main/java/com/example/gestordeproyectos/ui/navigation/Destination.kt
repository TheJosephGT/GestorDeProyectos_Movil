package com.example.gestordeproyectos.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String, val icon: ImageVector, val title: String) {
    object Login : Destination(
        route = "Login", icon = Icons.Filled.Person,
        title = "Login"
    )

    object Home : Destination(
        route = "Home", icon = Icons.Filled.Home,
        title = "Home"
    )

    object RegistroUsuario : Destination(
        route = "RegistroUsuario", icon = Icons.Filled.Star,
        title = "RegistroUsuario"
    )
    object ConsultaUsuarios : Destination(
        route = "Consulta", icon = Icons.Filled.Email,
        title = "Consulta"
    )

    object UpdateRegistroUsuarios : Destination(
        route = "Registro_Update", icon = Icons.Filled.Email,
        title = "Registro_Update"
    )
    object RegistroProyectos : Destination(
        route = "Registro_Proyecto", icon = Icons.Filled.Email,
        title = "Registro_Proyecto"
    )
    object UpdateRegistroProyectos : Destination(
        route = "Registro_UpdateProyecto", icon = Icons.Filled.Email,
        title = "Registro_UpdateProyecto"
    )
    object GestionarProyectos : Destination(
        route = "Gestionar_Proyecto", icon = Icons.Filled.Email,
        title = "Gestionar_Proyecto"
    )


    companion object {
        val toList = listOf(Home)
    }
}