package com.example.gestordeproyectos.ui.theme.registros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrarTarea() {
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
            Text(
                text = "Registro tarea",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Nombre de la tarea") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                leadingIcon = { Icon(Icons.Filled.Work, contentDescription = "Proyecto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Descripción") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                leadingIcon = { Icon(Icons.Filled.Description, contentDescription = "Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Seleccionar participante") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                leadingIcon = { Icon(Icons.Filled.Group, contentDescription = "Participante") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(size = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x57BBC0C7))
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Jorge",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }
                }

            Spacer(modifier = Modifier.height(16.dp))

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
                    text = "Crear",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Panel de control",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                Modifier
                    .shadow(
                        elevation = 48.dp,
                        spotColor = Color(0x0A000000),
                        ambientColor = Color(0x0A000000)
                    )
                    .padding(0.dp)
                    .width(386.dp)
                    .height(184.dp)
                    .background(color = Color(0xFFE6ECF5), shape = RoundedCornerShape(size = 12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Lavar la casa", fontSize = 18.sp, color = Color.Black)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Integrantes:", fontSize = 14.sp, color = Color.Black)

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        Text("Jorge  ->", fontSize = 14.sp, color = Color.Black)

                        Spacer(modifier = Modifier.width(16.dp))

                        Text("Incompleto", fontSize = 14.sp, color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        Text("Joseph  ->", fontSize = 14.sp, color = Color.Black)

                        Spacer(modifier = Modifier.width(16.dp))

                        Text("Incompleto", fontSize = 14.sp, color = Color.Green)
                    }


                }

            }

        }
    }
}
