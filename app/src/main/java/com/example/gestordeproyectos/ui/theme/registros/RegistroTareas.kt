package com.example.gestordeproyectos.ui.theme.registros

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gestordeproyectos.data.dto.ParticipantesProyectosDTO
import com.example.gestordeproyectos.data.dto.ParticipantesTareasDTO
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import com.example.gestordeproyectos.ui.viewModel.ProyectoViewModel
import com.example.gestordeproyectos.ui.viewModel.TareaViewModel
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "SuspiciousIndentation"
)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun RegistrarTarea(idProyectoActual: Int, navController: NavController, viewModel: TareaViewModel = hiltViewModel(), viewModelProyecto: ProyectoViewModel = hiltViewModel(), viewModelUsuario: LoginViewModel = hiltViewModel()) {
    DisposableEffect(Unit) {
        viewModel.cargar(idProyectoActual)
        viewModelProyecto.getProyectoId(idProyectoActual)
        viewModelProyecto.cargarUsuarios(idProyectoActual)
        onDispose {}
    }

    val ListParticipantesProyecto by viewModelProyecto.ListParticipantesProyecto.collectAsStateWithLifecycle()
    val uiStateUsuario by viewModelUsuario.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    var participantesSeleccionados by remember {
        mutableStateOf(mutableListOf<UsuariosDto>())
    }


    //Dropdown
    var expandedRol by remember { mutableStateOf(false) }
    var expandedNickName by remember { mutableStateOf(false) }
    var rolSeleccionado by remember { mutableStateOf("") }
    var nickNameSeleccionado by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expandedRol || expandedNickName) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
                .height(900.dp)
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
                        fontSize = 24.sp, // Establece el tamaño que prefieras
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                )
            }
            Spacer(modifier = Modifier.height(35.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Button(
                    onClick = {
                        viewModel.proyectoId = idProyectoActual
                        viewModel.send()
                        navController.navigate(Destination.Home.route)
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
                        text = "Crear Tarea",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Registra tarea",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = {viewModel.nombre = it},
                    label = { Text("Nombre de la tarea") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    leadingIcon = { Icon(Icons.Filled.Work, contentDescription = "Proyecto") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = { viewModel.descripcion = it},
                    label = { Text("Descripción") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    leadingIcon = { Icon(Icons.Filled.Description, contentDescription = "Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.prioridad,
                    onValueChange = {viewModel.prioridad = it},
                    label = { Text("Prioridad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    leadingIcon = { Icon(Icons.Filled.Description, contentDescription = "Prioridad") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    OutlinedTextField(
                        value = rolSeleccionado,
                        onValueChange = {
                            rolSeleccionado = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFiledSize = coordinates.size.toSize()
                            },
                        label = { Text("Seleccionar rol participante") },
                        trailingIcon = {
                            Icon(icon, "", Modifier.clickable { expandedRol = !expandedRol })
                        },
                        readOnly = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )
                    DropdownMenu(expanded = expandedRol,
                        onDismissRequest = { expandedRol = false },
                        modifier = Modifier.width(
                            with(LocalDensity.current) { textFiledSize.width.toDp() }
                        )) {
                    val rolesUsuarios = ListParticipantesProyecto.usuarios.map { it.rol }
                    rolesUsuarios.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            rolSeleccionado = label
                            expandedRol = false
                        })
                    }
                 }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    OutlinedTextField(
                        value = nickNameSeleccionado,
                        onValueChange = {
                            nickNameSeleccionado = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFiledSize = coordinates.size.toSize()
                            },
                        label = { Text("Seleccionar participante") },
                        trailingIcon = {
                            Icon(
                                icon,
                                "",
                                Modifier.clickable { expandedNickName = !expandedNickName })
                        },
                        readOnly = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        enabled = rolSeleccionado.isNotEmpty()
                    )
                    DropdownMenu(expanded = expandedNickName,
                        onDismissRequest = { expandedNickName = false },
                        modifier = Modifier.width(
                            with(LocalDensity.current) { textFiledSize.width.toDp() }
                        )) {
                    val nombresUsuariosConRolSeleccionado = ListParticipantesProyecto.usuarios
                        .filter { it.rol == rolSeleccionado }
                        .map { it.nickName }

                    nombresUsuariosConRolSeleccionado.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            nickNameSeleccionado = label
                            expandedNickName = false
                        })
                    }
                     }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        keyboardController?.hide()
                        val usuario = uiStateUsuario.usuarios.find { it.nickName == nickNameSeleccionado }
                        if (usuario != null) {
                            val participanteTarea = ParticipantesTareasDTO(usuarioId = usuario.usuarioId)
                                viewModel.participantes.add(participanteTarea)
                                participantesSeleccionados.add(usuario)
                        }
                        rolSeleccionado = ""
                        nickNameSeleccionado = ""
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(size = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E4AAB),
                        contentColor = Color(0xFF2E4AAB)
                    )
                ) {
                    Text(
                        text = "Seleccionar participante",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "USUARIOS SELECCIONADOS",
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
}
