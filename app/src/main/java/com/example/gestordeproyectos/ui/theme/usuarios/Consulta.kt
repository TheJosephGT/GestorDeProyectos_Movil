package com.example.gestordeproyectos.ui.theme.usuarios

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.ui.viewModel.LoginViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items

@Composable
fun Consulta(usuarios: List<UsuariosDto>, navController: NavController, onUsuarioClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Lista de usuarios", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(usuarios) { usuario ->
                UsuarioItem(usuario, navController = navController){
                    onUsuarioClick(it)
                }
            }
        }
    }
}

@Composable
fun UsuarioItem(
    usuario: UsuariosDto,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    onUsuarioClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { usuario.usuarioId?.let { onUsuarioClick(it) } }),
    ) {
        Column(
            modifier = Modifier.padding(16.dp).clickable(onClick = { usuario.usuarioId?.let { onUsuarioClick(it) } }),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = usuario.nickName, style = MaterialTheme.typography.titleMedium)
            Text(text = usuario.nombreCompleto, style = MaterialTheme.typography.titleMedium)
            Text(text = usuario.correo, style = MaterialTheme.typography.titleMedium)
            Text(text = usuario.rol, style = MaterialTheme.typography.titleMedium)
        }
    }
}