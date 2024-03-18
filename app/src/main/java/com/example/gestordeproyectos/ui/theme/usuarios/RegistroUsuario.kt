package com.example.gestordeproyectos.ui.theme.usuarios

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val contrasenaConfirmacion = remember { mutableStateOf("") }
    val registroExitoso = remember {
        mutableStateOf(false)
    }
    val allFieldsCompleted = remember {
        mutableStateOf(false)
    } // Si todo los campos estan validando
    val scrollState = rememberScrollState()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).height(900.dp)) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF2E4AAB))
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                IconButton(
                    onClick = { navController.navigateUp() }, // Reemplaza esto con la acción de navegación correcta
                    modifier = Modifier.align(Alignment.TopStart).padding(start = 16.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = "ProTasker", style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White
                    ), modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Registro", style = TextStyle(
                            fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.Black
                        ), modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(value = viewModel.nickName,
                        onValueChange = { viewModel.nickName = it },
                        label = { Text("Nickname") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person, contentDescription = "Nickname"
                            )
                        })
                    if (!viewModel.nickNameError && viewModel.nickName.isBlank()) {
                        Text(
                            text = "Debe ingresar un nickname", color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(value = viewModel.nombreCompleto,
                        onValueChange = { viewModel.nombreCompleto = it },
                        label = { Text("Nombre completo") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Badge, contentDescription = "Nombre completo"
                            )
                        })
                    if (!viewModel.nombreCompletoError && viewModel.nombreCompleto.isBlank()) {
                        Text(
                            text = "Debe ingresar un nombre completo.", color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(value = viewModel.rol,
                        onValueChange = { viewModel.rol = it },
                        label = { Text("Rol") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Badge, contentDescription = "Rol"
                            )
                        })
                    if (!viewModel.rolError && viewModel.rol.isBlank()) {
                        Text(
                            text = "Debe ingresar un rol.", color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(value = viewModel.correo,
                        onValueChange = { viewModel.correo = it },
                        label = { Text("Correo Electrónico") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email, contentDescription = "Correo Electrónico"
                            )
                        })
                    val correoPattern = Regex("^\\S+@\\S+\\.com\$")
                    if (viewModel.correoError && viewModel.correo.isNotBlank()) {
                        if (!correoPattern.matches(viewModel.correo)) {
                            Text(
                                text = "El formato del correo electrónico es incorrecto.",
                                color = Color.Red
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.clave,
                        onValueChange = { viewModel.clave = it },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock, contentDescription = "Contraseña"
                            )
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (viewModel.claveError) {
                        Text(
                            text = "La contraseña debe tener al menos 6 caracteres.",
                            color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    OutlinedTextField(
                        value = contrasenaConfirmacion.value,
                        onValueChange = { contrasenaConfirmacion.value = it },
                        label = { Text("Confirmar contraseña") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock, contentDescription = "Confirmar Contraseña"
                            )
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (contrasenaConfirmacion.value != viewModel.clave) {
                        Text(
                            text = "Las contraseñas no coinciden.", color = Color.Red
                        )
                    }


                    Spacer(modifier = Modifier.height(24.dp))

                    // validacion para el boton
                    allFieldsCompleted.value =
                        viewModel.nickName.isNotBlank() && viewModel.nombreCompleto.isNotBlank() && viewModel.correo.isNotBlank() && viewModel.clave.isNotBlank() && viewModel.clave.length >= 6 && contrasenaConfirmacion.value == viewModel.clave

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if (allFieldsCompleted.value && viewModel.ValidarRegistro() && viewModel.ValidarRegistro()) {
                                viewModel.send()
                                viewModel.createUserWithEmailAndPassword(viewModel.correo, viewModel.clave){
                                    navController.navigate(Destination.Home.route)
                                }
                                registroExitoso.value = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E4AAB)
                        )
                    ) {
                        Text(
                            text = "Registrar", color = Color.White, fontSize = 18.sp
                        )
                    }

                }
            }
        }
        if (viewModel.registerError) {
            Snackbar(
                action = {
                    TextButton(
                        onClick = { viewModel.registerError = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(text = "OK")
                    }
                }, modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Error al registrar. Por favor, verifica tus datos.", color = Color.Red
                )
            }
            LaunchedEffect(Unit) {
                delay(10000)
                viewModel.registerError = false
            }
        }
        if (registroExitoso.value) {
            Snackbar(
                action = {
                    TextButton(
                        onClick = { registroExitoso.value = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(text = "OK")
                    }
                }, modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "¡Registro exitoso!", color = Color.Green
                )
            }
            LaunchedEffect(Unit) {
                delay(3000) // Delay de 3 segundos
            }

        }
    }
}