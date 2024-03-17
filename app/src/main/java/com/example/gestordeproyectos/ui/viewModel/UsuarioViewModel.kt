package com.example.gestordeproyectos.ui.viewModel

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.gestordeproyectos.data.dto.UsuariosDto
import com.example.gestordeproyectos.data.repository.UsuariosRepository
import com.example.gestordeproyectos.ui.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    var usuarioId by mutableStateOf(1)
    var nickName by mutableStateOf("")
    var nombreCompleto by mutableStateOf("")
    var correo by mutableStateOf("")
    var rol by mutableStateOf("")
    var clave by mutableStateOf("")

    var nickNameError by mutableStateOf(true)
    var nombreCompletoError by mutableStateOf(true)
    var correoError by mutableStateOf(true)
    var claveError by mutableStateOf(true)
    var rolError by mutableStateOf(true)

    var loginError by mutableStateOf(false)
    var registerError by mutableStateOf(false)

    val auth: FirebaseAuth = Firebase.auth
    private val _loading = mutableStateOf(false)

    fun ValidarLogin(): Boolean {

        correoError = correo.isNotEmpty()
        claveError = clave.isNotEmpty()

        return correoError && claveError
    }
    fun ValidarRegistro(): Boolean {

        nickNameError = nickName.isNotEmpty()
        nombreCompletoError = nombreCompleto.isNotEmpty()
        correoError = correo.isNotEmpty()
        claveError =  clave.isEmpty() && clave.length < 6
        rolError = rol.isNotEmpty()

        return !(nickNameError && nombreCompletoError && correoError && claveError)
    }


    private val _uiState = MutableStateFlow(UsuarioListState())
    val uiState: StateFlow<UsuarioListState> = _uiState.asStateFlow()

    val usuarios: StateFlow<Resource<List<UsuariosDto>>> = usuariosRepository.getUsuarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )

    fun singInWithEmailAndPassword(correo: String, clave:String, home: () -> Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(correo, clave)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Log.d("Se ejecuto el Login", "singInWithEmailAndPassword logueado!!")
                            home()
                        }else{
                            Log.d("Se ejecuto el Login", "singInWithEmailAndPassword: ${task.result.toString()}")
                        }
                    }
            }catch (ex: Exception){
                Log.d("Se ejecuto el Login", "singInWithEmailAndPassword: ${ex.message}")
            }
        }
    }

    fun createUserWithEmailAndPassword(correo: String, clave:String, home: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            val authNewUser = FirebaseAuth.getInstance()
            authNewUser.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d("Se ejecuto el register", "Usuario creado con éxito: ${task.result?.user?.uid}")
                        authNewUser.signOut()
                        home()
                    }else{
                        Log.d("Se ejecuto el register", "createUserWithEmailAndPassword: ${task.exception?.message}")
                    }
                    _loading.value = false
                }
        }
    }
    fun singOut(login: () -> Unit){
        Firebase.auth.signOut()
        login()
    }
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
        if (ValidarRegistro()) {
            viewModelScope.launch {
                val usuarios = UsuariosDto(
                    nickName = nickName,
                    nombreCompleto = nombreCompleto,
                    correo = correo,
                    rol = rol,
                    clave = clave
                )
                usuariosRepository.postUsuarios(usuarios)
                clear()
                cargar()
            }
        } else {
            registerError = true
        }
    }

    fun updateUsuario(){
        if (ValidarRegistro()) {
            viewModelScope.launch {
                usuariosRepository.putUsuario(
                    usuarioId, UsuariosDto(
                        usuarioId = usuarioId,
                        nickName = nickName,
                        nombreCompleto = nombreCompleto,
                        correo = correo,
                        rol = rol,
                        clave = clave
                    )
                )
                clear()
                cargar()
            }
        } else {
            registerError = true
        }
    }

    fun getUsuarioById(id: Int) {
        usuarioId = id
        clear()
        usuariosRepository.getUsuarioId(usuarioId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                        _uiState.update{ it.copy(usuario = result.data)
                        }
                    nickName = uiState.value.usuario!!.nickName
                    nombreCompleto = uiState.value.usuario!!.nombreCompleto
                    correo = uiState.value.usuario!!.correo
                    rol = uiState.value.usuario!!.rol
                    clave = uiState.value.usuario!!.clave
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clear(){
        nickName = ""
        nombreCompleto = ""
        correo = ""
        clave = ""
    }
}