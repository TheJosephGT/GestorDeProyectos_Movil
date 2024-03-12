package com.example.gestordeproyectos.ui.theme.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeproyectos.data.repository.LoginRepository
import com.example.gestordeproyectos.ui.theme.remote.dto.LoginRequest
import com.example.gestordeproyectos.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val loginState: StateFlow<Resource<Unit>> get() = _loginState

    fun login(correo: String, clave: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            val request = LoginRequest(correo, clave)
            val result = loginRepository.login(request).first()
            _loginState.value = result
        }
    }
}