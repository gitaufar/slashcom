package com.example.slashcom.data.firebase

import android.util.Log
import com.example.slashcom.cache.UserData
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.model.User
import com.example.slashcom.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    private val database = FirebaseProvider.database

    override fun register(
        username: String,
        email: String,
        password: String,
        isIbu: Boolean,
        onResult: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid
                    if (userId != null) {
                        val user = User(username, email, isIbu)
                        database.child("user")
                            .child(userId)
                            .setValue(user)
                            .addOnSuccessListener {
                                onResult(true, null)
                            }
                            .addOnFailureListener { e ->
                                onResult(false, e.message)
                            }
                    } else {
                        onResult(false, "Gagal mendapatkan user ID")
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    override fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid ?: ""
                    database.child("user").child(uid).get().addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val userData = snapshot.getValue(User::class.java)
                            UserData.userName = userData?.username ?: ""
                            UserData.uid = uid
                            UserData.isIbu = userData?.isIbu ?: false
                            onResult(true, null)
                            Log.d("Firebase", "Data: $userData")
                        } else {
                            Log.d("Firebase", "Data tidak ada")
                        }
                    }.addOnFailureListener { error ->
                        Log.e("Firebase", "Gagal: ${error.message}")
                    }
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthInvalidUserException -> "Email tidak terdaftar"
                        is FirebaseAuthInvalidCredentialsException -> "Password salah"
                        is FirebaseAuthException -> "Firebase error: ${exception.errorCode}"
                        else -> exception?.message ?: "Login gagal"
                    }
                    onResult(false, errorMessage)
                }
            }
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
