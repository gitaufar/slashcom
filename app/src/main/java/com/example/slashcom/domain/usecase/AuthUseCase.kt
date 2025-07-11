package com.example.slashcom.domain.usecase

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.UserData
import com.example.slashcom.cache.dataStore
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.repository.AuthRepository

class AuthUseCase(private val repository: AuthRepository) {

    fun register(username: String,email: String, password: String, confirmPassword: String, isIbu: Boolean, onResult: (Boolean, String?) -> Unit) {
        val validatePassword = validateRegistration(username , email, password, confirmPassword)
        if(validatePassword != null){
            onResult(false, validatePassword)
            return
        }
        repository.register(username.trim(),email.trim(), password, isIbu, onResult)
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val validateLogin = validateLogin(email, password)
        if(validateLogin != null){
            onResult(false, validateLogin)
            return
        }
        repository.login(email.trim(), password.trim(), onResult)
    }

    suspend fun saveUid(context: Context, uid: String, onResult: (Boolean, String?) -> Unit) {
        val ref = FirebaseProvider.database.child("ibu")
        try {
            val trimmedUid = uid.trim()
            val exists = getIbuUidById(trimmedUid)

            if (exists == null) {
                onResult(false, "Tidak ada UID yang cocok")
                return
            }

            context.dataStore.edit { preferences ->
                repository.setPendampingForIbu(repository.getCurrentUserId() ?: "", exists)
                preferences[PreferencedKey.uidIbu] = exists
            }
            onResult(true, null)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(false, "Gagal menyimpan UID")
        }
    }

    suspend fun getIbuUidById(randomCode: String): String? {
        return repository.getIbuUidById(randomCode)
    }

    private fun validateRegistration(username: String, email: String, password: String, confirmPassword: String): String? {
        if (username.trim().isEmpty()){
            return "Username tidak boleh kosong"
        }

        if (confirmPassword != password) {
            return "Password dan confirm password berbeda"
        }

        if (password.length < 8) {
            return "Password minimal 8 karakter"
        }
        if (!password.any { it.isDigit() }) {
            return "Password harus mengandung angka"
        }
        if (!email.contains("@")) {
            return "Email tidak valid"
        }
        return null
    }

    private fun validateLogin(email: String, password: String): String? {
        if(email.trim().isEmpty()){
            return "Email tidak boleh kosong"
        }

        if(password.trim().isEmpty()){
            return "Password tidak boleh kosong"
        }
        return null
    }

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }

    fun logout() {
        repository.logout()
    }
}
