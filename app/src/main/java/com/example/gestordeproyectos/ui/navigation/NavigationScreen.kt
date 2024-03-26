package com.example.gestordeproyectos.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestordeproyectos.ui.theme.administracion.GestionarProyectos
import com.example.gestordeproyectos.ui.theme.administracion.GestionarTareas
import com.example.gestordeproyectos.ui.theme.home.Home
import com.example.gestordeproyectos.ui.theme.registros.RegistrarProyectos
import com.example.gestordeproyectos.ui.theme.registros.RegistrarProyectosEdit
import com.example.gestordeproyectos.ui.theme.registros.RegistrarTarea
import com.example.gestordeproyectos.ui.theme.registros.RegistrarTareaEdit
import com.example.gestordeproyectos.ui.theme.usuarios.Consulta
import com.example.gestordeproyectos.ui.theme.usuarios.LoginScreen
import com.example.gestordeproyectos.ui.theme.usuarios.RegisterScreen
import com.example.gestordeproyectos.ui.theme.usuarios.RegisterScreenEdit
import com.example.gestordeproyectos.ui.theme.vistas.DetalleTarea
import com.example.gestordeproyectos.ui.util.Resource
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            if(currentDestination.value?.destination?.route !in listOf(Destination.Login.route, Destination.RegistroUsuario.route)){
                BottomNavigationBar(navController = navController, appItems = Destination.toList)
            }
                     },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavigation(navController = navController)
            }
        }
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppNavigation(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    NavHost(
        navController,
        startDestination = if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            Destination.Login.route
        } else {
            Destination.Home.route
        }
    ) {
        composable(Destination.Login.route) {
            LoginScreen(navController)
        }
        composable(Destination.Home.route) {
            Home(navController = navController)
        }
        composable(Destination.RegistroUsuario.route){
            RegisterScreen(navController = navController)
        }
        composable(Destination.RegistroProyectos.route){
            RegistrarProyectos(navController = navController)
        }
        composable(Destination.GestionarProyectos.route) {
            GestionarProyectos(navController = navController)
        }
        composable(
            route = Destination.RegistrarTarea.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { capturar ->
            val proyectoId = capturar.arguments?.getInt("id") ?: 0

            RegistrarTarea(idProyectoActual = proyectoId, navController = navController)
        }
        composable(
            route = Destination.GestionarTarea.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { capturar ->
            val proyectoId = capturar.arguments?.getInt("id") ?: 0

            GestionarTareas(idProyectoActual = proyectoId, navController = navController)
        }
        composable(Destination.ConsultaUsuarios.route) {
            val usuariosResource by viewModel.usuarios.collectAsState(initial = Resource.Loading())
            val usuarios = usuariosResource.data

            if (usuarios != null) {
                Consulta(usuarios = usuarios, navController){
                        id -> navController.navigate(Destination.UpdateRegistroUsuarios.route + "/${id}")
                }
            }
        }
        composable(
            route = Destination.UpdateRegistroUsuarios.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { capturar ->
            val usuarioId = capturar.arguments?.getInt("id") ?: 0

            RegisterScreenEdit(usuarioId = usuarioId, navController = navController) {
                navController.navigate(Destination.Login.route)
            }
        }
        composable(
            route = Destination.UpdateRegistroProyectos.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { capturar ->
            val proyectoId = capturar.arguments?.getInt("id") ?: 0

            RegistrarProyectosEdit(idProyectoActual = proyectoId, navController = navController) {
                navController.navigate(Destination.RegistroProyectos.route)
            }
        }
        composable(
            route = Destination.UpdateRegistroTareas.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { capturar ->
            val tareaId = capturar.arguments?.getInt("id") ?: 0

            RegistrarTareaEdit(idTareaActual = tareaId, navController = navController) {
                navController.navigate(Destination.GestionarProyectos.route)
            }
        }
        composable(
            route = Destination.DetalleTarea.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { capturar ->
            val tareaId = capturar.arguments?.getInt("id") ?: 0

            DetalleTarea(idTareaActual = tareaId, navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, appItems: List<Destination>) {

    BottomAppBar(
        containerColor = Color(0xFF2E4AAB),
        contentColor = Color.White
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            items(appItems) { appitem ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(appitem.route)
                        }
                        .padding(horizontal = 33.dp)
                ) {
                    Icon(
                        imageVector = appitem.icon,
                        contentDescription = appitem.title
                    )
                    Text(
                        text = appitem.title,
                        color = Color.White
                    )
                }
            }
        }
    }
}