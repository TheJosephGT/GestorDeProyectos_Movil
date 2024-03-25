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
import com.example.gestordeproyectos.data.dto.ParticipantesTareasDTO
import com.example.gestordeproyectos.data.dto.ProyectosDto
import com.example.gestordeproyectos.data.dto.TareasDto
import com.example.gestordeproyectos.data.repository.ProyectosRepository
import com.example.gestordeproyectos.data.repository.TareasRepository
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

data class TareaListState(
    val isLoading: Boolean = false,
    val tareas: List<TareasDto> = emptyList(),
    val tarea: TareasDto? = null,
    val error: String = "",
)
@SuppressLint("MutableCollectionMutableState")
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class TareaViewModel @Inject constructor(
    private val tareasRepository: TareasRepository
) : ViewModel() {
    var tareaId by mutableStateOf(1)
    var proyectoId by mutableStateOf(0)
    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var estado by mutableStateOf("Activo")
    var prioridad by mutableStateOf("")
    var participantes by mutableStateOf(mutableListOf<ParticipantesTareasDTO>())

    var nombreError by mutableStateOf(true)
    var descripcionError by mutableStateOf(true)
    var prioridadError by mutableStateOf(true)

    private val _uiState = MutableStateFlow(TareaListState())
    val uiState: StateFlow<TareaListState> = _uiState.asStateFlow()

    private val _ListaTareasPorProyecto = MutableStateFlow(TareaListState())
    val ListaTareasPorProyecto: StateFlow<TareaListState> = _ListaTareasPorProyecto.asStateFlow()

    init {
        cargar()
    }
    fun validarTarea(): Boolean {
        nombreError = nombre.isNotBlank()
        descripcionError = descripcion.isNotBlank()
        prioridadError = prioridad.toIntOrNull() != null

        return nombreError && descripcionError && prioridadError
    }
    fun cargarTareasPorIdProyecto(id: Int) {
        tareasRepository.getTareasByProyectoId(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _ListaTareasPorProyecto.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _ListaTareasPorProyecto.update { it.copy(tareas = result.data ?: emptyList()) }

                }

                is Resource.Error -> {
                    _ListaTareasPorProyecto.update { it.copy(error = result.message ?: "Error desconocido") }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun cargar() {
        tareasRepository.getTareas().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(tareas = result.data ?: emptyList()) }

                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun ValidarTarea(): Boolean {
        nombreError = nombre.isNotBlank()
        descripcionError = descripcion.isNotBlank()

        return nombreError && descripcionError
    }

    fun send() {
        viewModelScope.launch {
            if (validarTarea()) {
                val tarea = TareasDto(
                    nombre = nombre,
                    proyectoId = proyectoId,
                    descripcion = descripcion,
                    estado = estado,
                    prioridad = prioridad,
                    participantes = participantes
                )
                tareasRepository.postTareas(tarea)
                cargar()
                clear()
                println("Tarea enviada correctamente")
            } else {
                println("No se puede enviar la tarea porque algunos campos están vacíos.")
            }
        }
    }

    fun updateTarea(){
        if(ValidarTarea()){
            viewModelScope.launch {
                tareasRepository.putTarea(
                    tareaId, TareasDto(
                        tareaId = tareaId,
                        nombre = nombre,
                        proyectoId = proyectoId,
                        descripcion = descripcion,
                        estado = estado,
                        prioridad = prioridad,
                        participantes = participantes
                    )
                )
                cargar()
                clear()
            }
        }
    }

    fun getTareaById(id: Int) {
        tareaId = id
        clear()
        tareasRepository.getTareaById(tareaId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update{ it.copy(tarea = result.data)
                    }
                    nombre = uiState.value.tarea!!.nombre
                    descripcion = uiState.value.tarea!!.descripcion
                    estado = uiState.value.tarea!!.estado
                    proyectoId = uiState.value.tarea!!.proyectoId!!
                    prioridad = uiState.value.tarea!!.prioridad
                    participantes = uiState.value.tarea!!.participantes
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun deleteTarea(id: Int) {
        viewModelScope.launch {
            tareasRepository.deleteTareas(id)
            cargar()
        }
    }


    fun clear(){
        nombre = ""
        descripcion = ""
        prioridad = ""
    }
}