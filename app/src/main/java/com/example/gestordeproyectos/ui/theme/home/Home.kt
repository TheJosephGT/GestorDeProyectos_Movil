package com.example.gestordeproyectos.ui.theme.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gestordeproyectos.ui.navigation.Destination
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Home(usuarioId: Int, viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    DisposableEffect(Unit) {
        viewModel.getUsuarioById(usuarioId)
        viewModel.cargar()
        onDispose {}
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color(0xFF2E4AAB))
                .fillMaxWidth()
                .height(270.dp))
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
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Gestor de tareas",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        ),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if(viewModel.usuario.rol == "Administrador"){
                    viewModel.usuario.usuarioId?.let { HomeAdmin(navController, usuarioActualId = it) }
                }else{
                    HomeNoAdmin()
                }
            }

        }
    }
}


@Composable
fun HomeAdmin(navController : NavController, usuarioActualId: Int){
    Button(
        onClick = {

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
            text = "Crear Proyectos",
            color = Color.White,
            fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(25.dp))

    Button(
        onClick = {

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
            text = "Gestionar proyectos",
            color = Color.White,
            fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(25.dp))

    Button(
        onClick = {
            navController.navigate("${Destination.RegistroUsuario.route}/${usuarioActualId}")
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
            text = "Crear Usuarios",
            color = Color.White,
            fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(25.dp))

    Button(
        onClick = {

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
            text = "Gestionar Usuarios",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Composable
fun HomeNoAdmin(){
    Spacer(modifier = Modifier.height(25.dp))

    Button(
        onClick = {

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
            text = "Ver proyectos",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}