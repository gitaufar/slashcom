package com.example.slashcom.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseProvider {
    val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
}

