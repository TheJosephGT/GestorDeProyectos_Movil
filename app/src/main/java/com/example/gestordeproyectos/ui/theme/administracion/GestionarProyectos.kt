package com.example.gestordeproyectos.ui.theme.administracion

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import com.example.gestordeproyectos.ui.viewModel.ProyectoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GestionarProyectos(viewModel: ProyectoViewModel = hiltViewModel(), navController: NavController, viewModelUsuario: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateUsuario by viewModelUsuario.uiState.collectAsStateWithLifecycle()
    val usuario = uiStateUsuario.usuarios.singleOrNull {
        it.correo == viewModelUsuario.auth.currentUser?.email && it.activo
    }
    if (usuario != null) {

        if(usuario.rol == "Administrador"){
            GestionarProyecto(uiState.proyectos, navController = navController, isAdmin = true)
        } else {
            usuario.usuarioId?.let { viewModel.cargarProyectosPorIdUsuario(it) }
            val ListProyectosPorUsuario by viewModel.ListProyectosPorUsuario.collectAsStateWithLifecycle()

            GestionarProyecto(proyectos = ListProyectosPorUsuario.proyectos, navController = navController, isAdmin = false)
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GestionarProyecto(proyectos: List<ProyectosDto>, navController: NavController, isAdmin: Boolean){
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
            Text(
                text = "ProTasker",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Proyectos",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()){
                items(proyectos) { proyecto ->
                    if(proyecto.activo){
                        ProyectoItem(proyecto, navController = navController, isAdmin = isAdmin)
                    }
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProyectoItem(proyecto: ProyectosDto, viewModel: ProyectoViewModel = hiltViewModel(), navController: NavController, isAdmin: Boolean){
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(color = Color(0xFFE6ECF5), shape = RoundedCornerShape(size = 12.dp))
            .clickable { navController.navigate(Destination.GestionarTarea.route + "/${proyecto.proyectoId}") }
    ) {
        Row(
            Modifier
                .padding(all = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isAdmin){
                IconButton(onClick = {navController.navigate(Destination.UpdateRegistroProyectos.route + "/${proyecto.proyectoId}")  }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF2E4AAB)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = proyecto.titulo,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = proyecto.descripcion,
                    color = Color.Gray
                )
            }
            if(isAdmin){
                IconButton(onClick = { proyecto.proyectoId?.let { viewModel.deleteProyecto(it) } }) {

                    Icon(
                        Icons.Filled.DeleteForever,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )

                }
            }
        }
    }
}