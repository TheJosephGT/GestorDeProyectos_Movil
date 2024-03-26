package com.example.gestordeproyectos.ui.theme.vistas


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.outlined.CheckCircleOutline
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaTareasPendiente() {
    var isChecked by remember { mutableStateOf(false) }


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

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Tareas Pendientes",
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
                    IconButton(onClick = { isChecked = !isChecked }) {
                        Icon(
                            imageVector = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircleOutline,
                            contentDescription = "Check",
                            tint = if (isChecked) MaterialTheme.colorScheme.primary else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = "Lavar la casa",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Este proyecto trata sobre un sis...",
                            color = Color.Gray
                        )
                    }
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = "Ver más",
                        tint = Color.Black
                    )
                }
            }

            // Tarjeta del Proyecto VerbLearn
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
                    IconButton(onClick = { isChecked = !isChecked }) {
                        Icon(
                            imageVector = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircleOutline,
                            contentDescription = "Check",
                            tint = if (isChecked) MaterialTheme.colorScheme.primary else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = "Planchar",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Este proyecto trata sobre un sis...",
                            color = Color.Gray
                        )
                    }
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = "Ver más",
                        tint = Color.Black
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
                    text = "Guardar",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

        }
    }
}