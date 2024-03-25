package com.example.gestordeproyectos.ui.theme.administracion

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
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
import com.example.gestordeproyectos.ui.viewModel.ProyectoViewModel
import com.example.gestordeproyectos.ui.viewModel.TareaViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GestionarTareas(idProyectoActual: Int, viewModel: TareaViewModel = hiltViewModel(), navController: NavController) {
    DisposableEffect(Unit) {
        viewModel.getTareaById(idProyectoActual)
        viewModel.cargar(idProyectoActual)
        onDispose {}
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

        Spacer(modifier = Modifier.height(15.dp))

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
                items(uiState.tareas) { tarea ->
                    if(tarea.activo){
                        TareaItem(tarea, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TareaItem(tarea: TareasDto, viewModel: ProyectoViewModel = hiltViewModel(), navController: NavController){
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(color = Color(0xFFE6ECF5), shape = RoundedCornerShape(size = 12.dp))

    ) {
        Row(
            Modifier
                .padding(all = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Aquí pones tu acción de edición */ }) {
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
            IconButton(onClick = { /* Aquí pones tu acción de edición */ }) {

                Icon(
                    Icons.Filled.DeleteForever,
                    contentDescription = "Delete",
                    tint = Color.Black
                )

            }

        }
    }
}