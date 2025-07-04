package com.example.slashcom.data.firebase

import android.util.Log
import androidx.compose.animation.core.snap
import com.example.slashcom.cache.UserData
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.model.IbuData
import com.example.slashcom.domain.model.User
import com.example.slashcom.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

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
                                if (user.isIbu) {
                                    generateUniqueCode { uniqueCode ->
                                        if (uniqueCode != null) {
                                            // Simpan ke path ibu/userId/id
                                            database.child("ibu")
                                                .child(userId)
                                                .child("id")
                                                .setValue(uniqueCode)
                                                .addOnSuccessListener {
                                                    UserData.isIbu = isIbu
                                                    onResult(true, null)
                                                }
                                                .addOnFailureListener { e ->
                                                    onResult(
                                                        false,
                                                        "Gagal simpan kode: ${e.message}"
                                                    )
                                                }
                                        } else {
                                            onResult(false, "Gagal generate kode unik")
                                        }
                                    }
                                } else {
                                    onResult(true, null)
                                }
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
                    val uid = getCurrentUserId() ?: ""
                    database.child("user").child(uid).get().addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val userData = snapshot.getValue(User::class.java)
                            UserData.userName = userData?.username ?: ""
                            UserData.uid = uid
                            UserData.isIbu = userData?.isIbu ?: false
                            UserData.email = userData?.email ?: ""
                            database.child("ibu").child(UserData.uid).get()
                                .addOnSuccessListener { snapshot ->
                                    if (snapshot.exists()) {
                                        val ibuData = snapshot.getValue(IbuData::class.java)
                                        UserData.id = ibuData?.id ?: "kosong idnya"
                                    }
                                }
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

    private fun generateUniqueCode(onComplete: (String?) -> Unit) {
        val maxAttempts = 10
        var attempt = 0

        fun tryGenerate() {
            if (attempt >= maxAttempts) {
                onComplete(null) // Gagal setelah 10x percobaan
                return
            }

            val randomCode = (100000..999999).random().toString()
            val ibuRef = database.child("ibu")

            ibuRef.orderByChild("id").equalTo(randomCode)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        attempt++
                        tryGenerate() // Coba lagi kalau sudah ada
                    } else {
                        onComplete(randomCode) // Kode unik, lanjut simpan
                    }
                }
                .addOnFailureListener {
                    onComplete(null) // Gagal akses DB
                }
        }

        tryGenerate()
    }

    override fun setPendampingForIbu(uidPendamping: String, uidIbu: String): Boolean {
        return try {
            val ref = FirebaseProvider.database
                .child("ibu")
                .child(uidIbu)
                .child("pendamping")
                .child(uidPendamping)

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        ref.setValue(true)
                        UserData.listPendamping += uidPendamping
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }
            })

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    override suspend fun getIbuUidById(randomCode: String): String? {
        val ibuRef = FirebaseProvider.database.child("ibu")
        return try {
            val snapshot = ibuRef.orderByChild("id").equalTo(randomCode).get().await()
            Log.d("FIREBASE_RESULT", "Children found: ${snapshot.childrenCount}")
            for (child in snapshot.children) {
                Log.d("FIREBASE_RESULT", "UID: ${child.key}, Data: ${child.value}")
                return child.key
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}
