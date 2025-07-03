package com.example.slashcom.data.repository

import android.content.Context
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.UserData
import com.example.slashcom.cache.dataStore
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.model.Mood
import com.example.slashcom.domain.repository.DashboardRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DashboardRepositoryImpl(
    private val database: DatabaseReference = FirebaseProvider.database,
    private val auth: FirebaseAuth = FirebaseProvider.auth
) : DashboardRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMoodsFromAyah(context: Context, uid: String): Flow<List<Mood>> {
        return getUid(context).flatMapLatest { uidIbu ->
            if (uidIbu == null) {
                flowOf(emptyList())
            } else {
                getMoods(uidIbu)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLastMoodFromAyah(context: Context, uid: String): Flow<Mood?> {
        return getUid(context).flatMapLatest { uidIbu ->
            uidIbu?.let { getLastMood(it) } ?: flowOf(null)
        }
    }


    override fun getMoods(uid: String): Flow<List<Mood>> = callbackFlow {
        val ref = database.child("ibu").child(uid).child("mood")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val moods = snapshot.children.mapNotNull { it.getValue(Mood::class.java) }
                if (UserData.listMoods != moods) {
                    UserData.listMoods = moods
                    trySend(moods).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        UserData.listMoods?.let { trySend(it).isSuccess }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }


    override fun getLastMood(uid: String): Flow<Mood?> = callbackFlow {
        val ref = database.child("ibu").child(uid).child("mood")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastMood = snapshot.children.lastOrNull()?.getValue(Mood::class.java)

                if (UserData.lastMood != lastMood) {
                    UserData.lastMood = lastMood
                    trySend(lastMood).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        UserData.lastMood?.let { trySend(it).isSuccess }

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

    fun getUid(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencedKey.uidIbu]
        }
    }
}
