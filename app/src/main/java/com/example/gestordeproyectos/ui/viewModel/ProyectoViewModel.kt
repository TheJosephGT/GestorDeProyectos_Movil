package com.example.gestordeproyectos.ui.viewModel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeproyectos.data.dto.ParticipantesProyectosDTO
import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.data.repository.ProyectosRepository
import com.example.gestordeproyectos.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProyectoListState(
    val isLoading: Boolean = false,
    val proyectos: List<ProyectosDto> = emptyList(),
    val proyecto: ProyectosDto? = null,
    val error: String = "",
)
@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class ProyectoViewModel @Inject constructor(
    private val proyectosRepository: ProyectosRepository
) : ViewModel() {
    var proyectoId by mutableStateOf(1)
    var titulo by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var estado by mutableStateOf("Activo")
    var fechaCreacion by mutableStateOf("2024-03-20T04:11:36.67")
    var progreso by mutableStateOf(0)
    var participantes by mutableStateOf(mutableListOf<ParticipantesProyectosDTO>())

    private val _uiState = MutableStateFlow(ProyectoListState())
    val uiState: StateFlow<ProyectoListState> = _uiState.asStateFlow()

    val proyectos: StateFlow<Resource<List<ProyectosDto>>> = proyectosRepository.getProyectos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )

    init {
        cargar()
    }

    fun cargar() {
        proyectosRepository.getProyectos().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(proyectos = result.data ?: emptyList()) }

                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun send() {
        viewModelScope.launch {
            println("Lista de participantes antes de enviar el proyecto: $participantes")
            val proyectos = ProyectosDto(
                titulo = titulo,
                descripcion = descripcion,
                estado = estado,
                fechaCreacion = fechaCreacion,
                progreso = progreso,
                participantes = participantes
            )
            proyectosRepository.postProyecto(proyectos)
            cargar()
            clear()
            println("Proyecto enviado correctamente")
        }
    }

    fun clear(){
        titulo = ""
        descripcion = ""
    }
}
