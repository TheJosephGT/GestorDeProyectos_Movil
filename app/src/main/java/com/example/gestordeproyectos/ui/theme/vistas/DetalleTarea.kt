package com.example.gestordeproyectos.ui.theme.vistas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gestordeproyectos.data.dto.TareasDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.theme.registros.UsuariosSeleccionadosScreen
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import com.example.gestordeproyectos.ui.viewModel.TareaViewModel

@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun DetalleTarea(idTareaActual: Int, viewModel: TareaViewModel = hiltViewModel(), navController: NavController, viewModelUsuario: LoginViewModel = hiltViewModel()) {
    DisposableEffect(Unit) {
        viewModel.getTareaById(idTareaActual)
        onDispose {}
    }
    val uiStateUsuario by viewModelUsuario.uiState.collectAsStateWithLifecycle()
    val usuario = uiStateUsuario.usuarios.singleOrNull {
        it.correo == viewModelUsuario.auth.currentUser?.email && it.activo
    }

    var participantesSeleccionados by remember {
        mutableStateOf(mutableListOf<UsuariosDto>())
    }

    participantesSeleccionados = viewModel.participantes.mapNotNull { participante ->
        // Buscar el UsuarioDTO con el mismo id que el usuarioId del participante
        uiStateUsuario.usuarios.find { usuario -> usuario.usuarioId == participante.usuarioId }
    }.toMutableList()

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
                text = "Detalle de la tarea",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
                    .padding(8.dp)
                    .shadow(1.dp, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBFB))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = viewModel.nombre,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,

                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Descripción de la tarea:",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = viewModel.descripcion,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row{
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2E4AAB))
                            .padding(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if(usuario != null && usuario.rol != "Administrador"){
                        Button(
                            onClick = {
                                      viewModel.participantes.removeIf{it.usuarioId == usuario.usuarioId && it.activo}
                                participantesSeleccionados.removeIf{ it.usuarioId == usuario.usuarioId && it.activo }
                                viewModel.updateTarea()
                                navController.navigate(Destination.GestionarProyectos.route)
                            },
                            Modifier
                                .height(50.dp),
                            shape = RoundedCornerShape(size = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E4AAB),
                                contentColor = Color(0xFF2E4AAB)
                            )
                        ) {
                            Text(
                                text = "Completado",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Usuarios con la tarea pendiente",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            UsuariosSeleccionadosScreen(participantesSeleccionados)
        }
    }
}

