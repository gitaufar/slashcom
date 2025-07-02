package com.example.slashcom.ui.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.data.firebase.AuthRepositoryImpl
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {

    val authRepository = AuthRepositoryImpl(FirebaseProvider.auth)
    val authUseCase = AuthUseCase(authRepository)

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading

        authUseCase.login(email, password) { success, errorMessage ->
            if (success) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error(errorMessage ?: "Login gagal")
            }
        }
    }

    fun register(email: String, password: String, confirmPass: String, username: String, isIbu: Boolean) {
        _registerState.value = RegisterState.Loading

        authUseCase.register(username,email, password, confirmPass, isIbu) { success, errorMessage ->
            if (success) {
                _registerState.value = RegisterState.Success
            } else {
                _registerState.value = RegisterState.Error(errorMessage ?: "Register gagal")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
