package com.example.slashcom.ui.presentation.auth

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.dataStore
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

    private val _verifikasiState = MutableStateFlow<State>(State.Idle)
    val verifikasiState: StateFlow<State> = _verifikasiState

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

    suspend fun saveUid(context: Context, uid: String) {
        _verifikasiState.value = State.Loading
        try {
            val trimmedUid = uid.trim()
            val exists = authUseCase.isIbuIdExists(trimmedUid)
            Log.d("cek uid", "exists: $exists")

            if (!exists) {
                _verifikasiState.value = State.Error("Tidak ada UID yang cocok")
                return
            }

            context.dataStore.edit { preferences ->
                preferences[PreferencedKey.uidIbu] = trimmedUid
            }
            _verifikasiState.value = State.Success
        } catch (e: Exception) {
            e.printStackTrace()
            _verifikasiState.value = State.Error("Gagal menyimpan UID")
        }
    }


}

sealed class State {
    object Idle : State()
    object Loading : State()
    object Success : State()
    data class Error(val message: String) : State()
}
