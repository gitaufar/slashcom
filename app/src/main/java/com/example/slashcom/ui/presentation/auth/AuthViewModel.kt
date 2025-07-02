package com.example.slashcom.ui.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.slashcom.data.firebase.AuthRepositoryImpl
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepositoryImpl(FirebaseProvider.auth)
    private val authUseCase = AuthUseCase(authRepository)

    private val _loginState = MutableStateFlow<State>(State.Idle)
    val loginState: StateFlow<State> = _loginState

    private val _registerState = MutableStateFlow<State>(State.Idle)
    val registerState: StateFlow<State> = _registerState

    fun login(email: String, password: String) {
        _loginState.value = State.Loading

        authUseCase.login(email, password) { success, errorMessage ->
            _loginState.value = if (success) {

                State.Success
            } else {
                State.Error(errorMessage ?: "Login gagal")
            }
        }
    }

    fun register(
        email: String,
        password: String,
        confirmPass: String,
        username: String,
        isIbu: Boolean
    ) {
        _registerState.value = State.Loading

        authUseCase.register(
            username,
            email,
            password,
            confirmPass,
            isIbu
        ) { success, errorMessage ->
            _registerState.value = if (success) {
                State.Success
            } else {
                State.Error(errorMessage ?: "Register gagal")
            }
        }
    }

    fun resetRegisterState() {
        _registerState.value = State.Idle
    }

    fun resetLoginState() {
        _loginState.value = State.Idle
    }

}

sealed class State {
    object Idle : State()
    object Loading : State()
    object Success : State()
    data class Error(val message: String) : State()
}
