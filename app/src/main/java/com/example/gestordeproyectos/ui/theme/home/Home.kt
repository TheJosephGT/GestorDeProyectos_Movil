package com.example.gestordeproyectos.ui.theme.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskManagerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E4AAB))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box( // Placeholder for logo
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ProTasker",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f)
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Gestor de tareas",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { /* TODO: Implement create project action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E4AAB)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Crear proyecto",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO: Implement view projects action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E4AAB)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Ver proyectos",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
