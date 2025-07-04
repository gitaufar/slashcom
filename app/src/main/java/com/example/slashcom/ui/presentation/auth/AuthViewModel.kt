package com.example.slashcom.ui.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.dataStore
import com.example.slashcom.data.firebase.AuthRepositoryImpl
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authUseCase = AuthUseCase(AuthRepositoryImpl(FirebaseProvider.auth))

    private val _loginState = MutableStateFlow<State>(State.Idle)
    val loginState: StateFlow<State> = _loginState

    private val _registerState = MutableStateFlow<State>(State.Idle)
    val registerState: StateFlow<State> = _registerState

    private val _verifikasiState = MutableStateFlow<State>(State.Idle)
    val verifikasiState: StateFlow<State> = _verifikasiState

    fun login(email: String, password: String) {
        _loginState.value = State.Loading
        authUseCase.login(email, password) { success, errorMessage ->
            _loginState.value = if (success) State.Success else State.Error(errorMessage ?: "Login gagal")
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
        authUseCase.register(username, email, password, confirmPass, isIbu) { success, errorMessage ->
            _registerState.value = if (success) State.Success else State.Error(errorMessage ?: "Register gagal")
        }
    }

    fun verifyAndSaveUid(context: Context, uid: String) {
        _verifikasiState.value = State.Loading
        viewModelScope.launch {
            authUseCase.saveUid(context, uid) { success, errorMessage ->
                _verifikasiState.value = if (success) State.Success else State.Error(errorMessage ?: "Verifikasi gagal")
            }
        }
    }

    fun getCurrentUserId(): String? = authUseCase.getCurrentUserId()

    fun logout() = authUseCase.logout()

    fun resetLoginState() {
        _loginState.value = State.Idle
    }

    fun resetRegisterState() {
        _registerState.value = State.Idle
    }

    fun resetVerifikasiState() {
        _verifikasiState.value = State.Idle
    }
}

sealed class State {
    object Idle : State()
    object Loading : State()
    object Success : State()
    data class Error(val message: String) : State()
}
