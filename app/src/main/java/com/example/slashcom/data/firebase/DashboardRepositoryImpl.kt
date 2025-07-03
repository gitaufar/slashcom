package com.example.slashcom.data.repository

import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.model.Mood
import com.example.slashcom.domain.repository.DashboardRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DashboardRepositoryImpl(
    private val database: DatabaseReference = FirebaseProvider.database,
    private val auth: FirebaseAuth = FirebaseProvider.auth
) : DashboardRepository {

    override fun getMoods(uid: String): Flow<List<Mood>> = callbackFlow {
        val ref = database.child("ibu").child(uid).child("mood")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val moods = snapshot.children.mapNotNull { it.getValue(Mood::class.java) }
                trySend(moods).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    override fun getLastMood(uid: String): Flow<Mood?> = callbackFlow {
        val ref = database.child("ibu").child(uid).child("mood")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastMood = snapshot.children.lastOrNull()?.getValue(Mood::class.java)
                trySend(lastMood).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    override fun addMood(mood: Mood) {
        val uid = auth.currentUser?.uid ?: return
        val ref = database.child("ibu").child(uid).child("mood")

        ref.get().addOnSuccessListener { snapshot ->
            val nextId = (snapshot.children.count() + 1).toString()
            ref.child(nextId).setValue(mood)
        }
    }
}
