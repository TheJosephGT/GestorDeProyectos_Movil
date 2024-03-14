package com.example.gestordeproyectos.ui.viewModel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.gestordeproyectos.data.dto.RegisterRequest
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.data.repository.UsuariosRepository
import com.example.gestordeproyectos.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsuarioListState(
    val isLoading: Boolean = false,
    val usuarios: List<UsuariosDto> = emptyList(),
    val usuario: UsuariosDto? = null,
    val error: String = "",
)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuariosRepository: UsuariosRepository
) : ViewModel() {
    var usuario by mutableStateOf(UsuariosDto())
    var nickName by mutableStateOf("")
    var nombreCompleto by mutableStateOf("")
    var correo by mutableStateOf("")
    var clave by mutableStateOf("")

    var nickNameError by mutableStateOf(true)
    var nombreCompletoError by mutableStateOf(true)
    var correoError by mutableStateOf(true)
    var claveError by mutableStateOf(true)

    fun ValidarLogin(): Boolean {

        correoError = correo.isNotEmpty()
        claveError = clave.isNotEmpty()

        return (correoError || claveError)
    }

    fun ValidarRegistro(): Boolean {

        nickNameError = nickName.isNotEmpty()
        nombreCompletoError = nombreCompleto.isNotEmpty()
        correoError = correo.isNotEmpty()
        claveError = clave.isNotEmpty()

        return (nickNameError || nombreCompletoError || correoError || claveError)
    }

    private val _uiState = MutableStateFlow(UsuarioListState())
    val uiState: StateFlow<UsuarioListState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        usuariosRepository.getUsuarios().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(usuarios = result.data ?: emptyList()) }

                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun send() {
        viewModelScope.launch {
            val usuarios = RegisterRequest(
                nickName = nickName,
                nombreCompleto = nombreCompleto,
                correo = correo,
                clave = clave
            )
            usuariosRepository.postRegister(usuarios)
            clear()
            cargar()

        }
    }

    fun getUsuarioById(id: Int) {
        viewModelScope.launch {
            usuario = usuariosRepository.getUsuarioById(id)!!
        }
    }

    fun clear(){
        nickName = ""
        nombreCompleto = ""
        correo = ""
        clave = ""
    }
}