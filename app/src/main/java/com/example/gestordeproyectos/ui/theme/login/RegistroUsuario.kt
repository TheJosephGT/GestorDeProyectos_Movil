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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF2E4AAB))
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(
                    text = "ProTasker",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
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
                        text = "Registro",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.nickName,
                        onValueChange = {viewModel.nickName = it},
                        label = { Text("Nickname") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nickname") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.nombreCompleto,
                        onValueChange = {viewModel.nombreCompleto = it},
                        label = { Text("Nombre completo") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = "Nombre completo") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.correo,
                        onValueChange = {viewModel.correo = it},
                        label = { Text("Correo Electrónico") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Correo Electrónico") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.clave,
                        onValueChange = {viewModel.clave = it},
                        label = { Text("Contraseña") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Confirmar contraseña") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirmar Contraseña") },
                        visualTransformation = PasswordVisualTransformation()
                    )



                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            if(viewModel.ValidarRegistro()){
                                viewModel.send()
                                navController.navigate(Destination.Login.route)
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
                            text = "Registrar",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
