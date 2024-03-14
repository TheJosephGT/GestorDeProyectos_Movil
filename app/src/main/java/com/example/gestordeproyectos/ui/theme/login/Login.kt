package com.example.gestordeproyectos.ui.theme.login

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import kotlinx.coroutines.delay

//@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable

fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF2E4AAB))
                    .fillMaxWidth()
                    .height(270.dp)
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(100.dp))

                    Text(
                        text = "ProTasker",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Inicio de sesión",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            ),
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Column {
                            OutlinedTextField(
                                value = viewModel.correo,
                                onValueChange = { viewModel.correo = it },
                                label = { Text("Correo") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (!viewModel.correoError) {
                                Text(text = "El campo correo esta vacío", color = Color.Red)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Column {
                            OutlinedTextField(
                                value = viewModel.clave,
                                onValueChange = { viewModel.clave = it },
                                label = { Text("Contraseña") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (!viewModel.claveError) {
                                Text(text = "El campo clave esta vacío", color = Color.Red)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { navController.navigate(Destination.RegistroUsuario.route) }) {
                            Text("Regístrate")
                        }

                        TextButton(onClick = { /* TODO */ }) {
                            Text("¿Has olvidado tu contraseña?")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if (viewModel.ValidarLogin()) {
                                val usuario = uiState.usuarios.singleOrNull {
                                    it.correo == viewModel.correo && it.clave == viewModel.clave && it.activo
                                }
                                if (usuario != null) {
                                    navController.navigate("${Destination.Home.route}/${usuario.usuarioId}")
                                }
                            } else {
                                viewModel.loginError = true
                            }
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
                            text = "Inicia sesión",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            if (viewModel.loginError) {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = { viewModel.loginError = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                        ) {
                            Text(text = "OK")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Los datos no son correctos.",
                        color = Color.Red
                    )
                }
                LaunchedEffect(Unit) {
                    delay(10000)
                    viewModel.loginError = false
                }
            }
        }
    }
}