package com.example.gestordeproyectos.ui.theme.administracion

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.TareasDto
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import com.example.gestordeproyectos.ui.viewModel.ProyectoViewModel
import com.example.gestordeproyectos.ui.viewModel.TareaViewModel


@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GestionarTareas(idProyectoActual: Int, viewModel: TareaViewModel = hiltViewModel(), navController: NavController, viewModelUsuario: LoginViewModel = hiltViewModel()) {
    DisposableEffect(Unit) {
        viewModel.getTareaById(idProyectoActual)
        viewModel.cargarTareasPorIdProyecto(idProyectoActual)
        onDispose {}
    }
    val uiState by viewModel.ListaTareasPorProyecto.collectAsStateWithLifecycle()
    val uiStateUsuario by viewModelUsuario.uiState.collectAsStateWithLifecycle()
    val usuario = uiStateUsuario.usuarios.singleOrNull {
        it.correo == viewModelUsuario.auth.currentUser?.email && it.activo
    }
    if (usuario != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF2E4AAB))
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = "ProTasker",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            if(usuario.rol == "Administrador"){
                GestionarTareasAdmin(idProyectoActual = idProyectoActual, navController = navController, uiState.tareas)
            } else {
                Log.d("Tareas", "Cargando tareas para el usuario: ${usuario.usuarioId}")
                usuario.usuarioId?.let { viewModel.cargarTareasPorIdUsuario(it, idProyectoActual) }
                val ListTareasPorUsuario by viewModel.ListTareasPorUsuario.collectAsStateWithLifecycle()
                GestionarTareasUsuario(idProyectoActual = idProyectoActual, navController = navController, ListTareasPorUsuario.tareas)
            }
        }
    }
}


@Composable
fun GestionarTareasUsuario(idProyectoActual: Int, navController: NavController, tareas: List<TareasDto>) {
    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Tareas Pendientes",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()){
                items(tareas) { tarea ->
                    if(tarea.activo){
                        TareaItemUsuario(tarea, navController = navController, idProyectoActual)
                    }
                }
            }
    }
}

@Composable
fun TareaItemUsuario(tarea: TareasDto, navController: NavController, idProyectoActual: Int){
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(color = Color(0xFFE6ECF5), shape = RoundedCornerShape(size = 12.dp))
            .clickable { navController.navigate(Destination.DetalleTarea.route + "/${tarea.tareaId}") }

    ) {
        Row(
            Modifier
                .padding(all = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = tarea.nombre,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = tarea.descripcion,
                    color = Color.Gray
                )
            }
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = "Ver más",
                tint = Color.Black
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GestionarTareasAdmin(idProyectoActual: Int, navController: NavController, tareas: List<TareasDto>){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tareas pendientes",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate(Destination.RegistrarTarea.route + "/${idProyectoActual}")},
            Modifier.height(50.dp),
            shape = RoundedCornerShape(size = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E4AAB),
                contentColor = Color(0xFF2E4AAB)
            )
        ) {
            Text(
                text = "Añadir tarea",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(tareas) { tarea ->
                if(tarea.activo){
                    TareaItem(tarea, navController = navController)
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TareaItem(tarea: TareasDto, viewModel: TareaViewModel = hiltViewModel(), navController: NavController){
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(color = Color(0xFFE6ECF5), shape = RoundedCornerShape(size = 12.dp))
            .clickable { navController.navigate(Destination.DetalleTarea.route + "/${tarea.tareaId}") }

    ) {
        Row(
            Modifier
                .padding(all = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate(Destination.UpdateRegistroTareas.route + "/${tarea.tareaId}")   }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF2E4AAB)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = tarea.nombre,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = tarea.descripcion,
                    color = Color.Gray
                )
            }
            IconButton(onClick = {
                tarea.tareaId?.let { viewModel.deleteTarea(it) }
            }) {

                Icon(
                    Icons.Filled.DeleteForever,
                    contentDescription = "Delete",
                    tint = Color.Black
                )

            }

        }
    }
}