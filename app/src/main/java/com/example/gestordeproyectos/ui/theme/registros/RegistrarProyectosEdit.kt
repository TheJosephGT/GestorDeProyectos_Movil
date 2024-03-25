package com.example.gestordeproyectos.ui.theme.registros

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.ui.geometry.Size
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
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gestordeproyectos.data.dto.ParticipantesProyectosDTO
import com.example.gestordeproyectos.data.dto.ParticipantesTareasDTO
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import com.example.gestordeproyectos.ui.viewModel.ProyectoViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@Composable
fun RegistrarProyectosEdit(
    idProyectoActual : Int,
    navController: NavController,
    viewModel: ProyectoViewModel = hiltViewModel(),
    viewModelUsuario: LoginViewModel = hiltViewModel(),
    onSaveClick: () -> Unit
) {
    DisposableEffect(Unit) {
        viewModel.getProyectoId(idProyectoActual)
        onDispose {}
    }
    val uiStateUsuario by viewModelUsuario.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var participantesSeleccionados by remember {
        mutableStateOf(mutableListOf<UsuariosDto>())
    }

    participantesSeleccionados = viewModel.participantes.mapNotNull { participante ->
        // Buscar el UsuarioDTO con el mismo id que el usuarioId del participante
        uiStateUsuario.usuarios.find { usuario -> usuario.usuarioId == participante.usuarioId }
    }.toMutableList()


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

    // Definir estados para indicar si se ha tocado o no cada campo
    var tituloError by remember { mutableStateOf(false) }
    var descripcionError by remember { mutableStateOf(false) }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
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

                Button(
                    onClick = {
                        if (!tituloError && !descripcionError && viewModel.titulo.isNotBlank() && viewModel.descripcion.isNotBlank()) { // Verificar si no hay errores en los campos y si no están en blanco
                            keyboardController?.hide()
                            viewModel.updateProyecto()
                            onSaveClick()
                            navController.navigate(Destination.Home.route)
                        } else {
                            if (viewModel.titulo.isBlank()) {
                                tituloError = true
                            }
                            if (viewModel.descripcion.isBlank()) {
                                descripcionError = true
                            }
                        }
                    },
                    Modifier
                        .height(50.dp),
                    shape = RoundedCornerShape(size = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E4AAB),
                        contentColor = Color(0xFF2E4AAB)
                    ),
                    enabled = !(tituloError || descripcionError)
                ) {
                    Text(
                        text = "Editar proyecto",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "REGISTRA EL PROYECTO",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.titulo,
                    onValueChange = {
                        viewModel.titulo = it
                        tituloError = it.isBlank()
                    },
                    label = { Text("Nombre del proyecto") },
                    singleLine = true,
                    isError = tituloError,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = {
                        viewModel.descripcion = it;
                        descripcionError = it.isBlank()
                    },
                    label = { Text("Descripción") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Description,
                            contentDescription = "Descripción"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = descripcionError
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
                        val rolesUsuarios = uiStateUsuario.usuarios.map { it.rol }.filter { it != "Administrador" }
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
                        val nombresUsuariosConRolSeleccionado = uiStateUsuario.usuarios
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

                Spacer(modifier = Modifier.height(24.dp))
                var usuarioAgregadoMensaje by remember { mutableStateOf("") }

                Button(
                    onClick = {
                        keyboardController?.hide()
                        val usuario = uiStateUsuario.usuarios.find { it.nickName == nickNameSeleccionado }
                        if (usuario != null) {
                            val participanteProyecto = ParticipantesProyectosDTO(usuarioId = usuario.usuarioId)
                            if (!participantesSeleccionados.contains(usuario)) {
                                viewModel.participantes.add(participanteProyecto)
                                participantesSeleccionados.add(usuario)
                                // Actualizar el mensaje
                                usuarioAgregadoMensaje = "El usuario ${usuario.nickName} ha sido agregado al proyecto."
                            } else {
                                usuarioAgregadoMensaje = "El usuario ya está agregado al proyecto"
                            }
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
                        text = "Agregar participante",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = usuarioAgregadoMensaje,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (participantesSeleccionados.any { it.nickName == nickNameSeleccionado }) Color.Red else Color.Green,
                    )
                )


                Spacer(modifier = Modifier.height(24.dp))

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

@Composable
fun UsuariosSeleccionadosScreenEdit(usuarios: List<UsuariosDto>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(usuarios) { usuario ->
                UsuariosSeleccionadosItem(usuario)
            }
        }
    }
}

@Composable
fun UsuariosSeleccionadosItemEdit(
    usuario: UsuariosDto
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = usuario.nickName, style = MaterialTheme.typography.titleMedium)
            Text(text = usuario.nombreCompleto, style = MaterialTheme.typography.titleMedium)
            Text(text = usuario.correo, style = MaterialTheme.typography.titleMedium)
            Text(text = usuario.rol, style = MaterialTheme.typography.titleMedium)
        }
    }
}