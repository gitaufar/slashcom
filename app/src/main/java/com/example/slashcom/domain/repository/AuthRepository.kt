package com.example.slashcom.domain.repository

interface AuthRepository {
    fun register(username: String, email: String, password: String, isIbu: Boolean, onResult: (Boolean, String?) -> Unit)
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit)
    fun getCurrentUserId(): String?
    fun logout()
}
