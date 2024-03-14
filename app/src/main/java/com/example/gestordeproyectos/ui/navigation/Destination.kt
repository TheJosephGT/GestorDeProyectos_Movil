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
    object RegistrarProyecto : Destination(
        route = "RegistrarProyecto", icon = Icons.Filled.Star,
        title = "RegistrarProyecto"
    )


    companion object {
        val toList = listOf(Home)
    }
}