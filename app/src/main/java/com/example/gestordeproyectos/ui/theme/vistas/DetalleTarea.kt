package com.example.gestordeproyectos.ui.theme.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetalleTarea() {
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
                text = "Detalle de la tareas",
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
                        text = "Lavar la casa",
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
                        text = "Esta aplicación móvil te ofrece una manera fácil y rápida de buscar información detallada sobre verbos en inglés. Puedes obtener las formas básicas, el pasado participio y el pasado simple de cualquier verbo. Además, proporcionamos definiciones en inglés y la traducción al español para una comprensión más completa.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                IconButton(
                    onClick = { /* TODO: Handle back action */ },
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

            }
        }
    }
}

