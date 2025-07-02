package com.example.slashcom.domain.usecase

import com.example.slashcom.domain.repository.AuthRepository

class AuthUseCase(private val repository: AuthRepository) {

    fun register(username: String,email: String, password: String, confirmPassword: String, isIbu: Boolean, onResult: (Boolean, String?) -> Unit) {
        val validatePassword = validateRegistration(email, password, confirmPassword)
        if(validatePassword != null){
            onResult(false, validatePassword)
            return
        }
        repository.register(username,email, password, isIbu, onResult)
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        repository.login(email, password, onResult)
    }

    private fun validateRegistration(email: String, password: String, confirmPassword: String): String? {
        if(confirmPassword != password) {
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

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }

    fun logout() {
        repository.logout()
    }
}
