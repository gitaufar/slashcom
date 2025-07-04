package com.example.slashcom.data.repository

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.snap
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
    override fun getMoodsFromAyah(context: Context): Flow<List<Mood>> {
        return getUid(context).flatMapLatest { uidIbu ->
            Log.d("uid_ibu_list_mood", uidIbu.toString())
            if (uidIbu == null) {
                flowOf(emptyList())
            } else {
                getMoods(uidIbu)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLastMoodFromAyah(context: Context): Flow<Mood?> {
        return getUid(context).flatMapLatest { uidIbu ->
            Log.d("uid_ibu", uidIbu.toString())
            uidIbu?.let { getLastMood(it) } ?: flowOf(null)
        }
    }

    override fun getPendamping(uid: String): Flow<List<String>> = callbackFlow {
        val pendampingRef = database.child("ibu").child(uid).child("pendamping")
        Log.d("getPendamping", "Dipanggil untuk UID: $uid")

        val pendampingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Ambil key dari map sebagai UID pendamping
                val pendampingIds = snapshot.children.mapNotNull { it.key }

                Log.d("getPendamping", "Jumlah UID pendamping: ${pendampingIds.size}")

                if (pendampingIds.isEmpty()) {
                    Log.d("getPendamping", "Tidak ada pendamping ditemukan.")
                    trySend(emptyList()).isSuccess
                    return
                }

                // Ambil nama-nama pendamping berdasarkan UID
                ambilNamaPendamping(pendampingIds) { namaList ->
                    if (UserData.listPendamping != namaList) {
                        UserData.listPendamping = namaList
                        trySend(namaList).isSuccess
                        Log.d("getPendamping", "Berhasil ambil nama-nama: $namaList")
                    } else {
                        Log.d("getPendamping", "Nama pendamping sama dengan cache, tidak dikirim ulang.")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("getPendamping", "Database error: ${error.message}")
                close(error.toException())
            }
        }

        if (UserData.listPendamping.isNotEmpty()) {
            Log.d("getPendamping", "Kirim dari cache: ${UserData.listPendamping}")
            trySend(UserData.listPendamping).isSuccess
        }

        pendampingRef.addValueEventListener(pendampingListener)
        awaitClose { pendampingRef.removeEventListener(pendampingListener) }
    }

    private fun ambilNamaPendamping(
        ids: List<String>,
        onComplete: (List<String>) -> Unit
    ) {
        val databaseRef = FirebaseProvider.database
        val namaList = mutableListOf<String>()
        var processedCount = 0

        ids.forEach { id ->
            databaseRef.child("user").child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val username = snapshot.child("username").getValue(String::class.java)
                        if (username != null) {
                            namaList.add(username)
                            Log.d("ambilNamaPendamping", "Ambil nama untuk $id: $username")
                        } else {
                            Log.w("ambilNamaPendamping", "Username tidak ditemukan untuk $id")
                        }
                        selesaiCek()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("ambilNamaPendamping", "Gagal ambil data user $id: ${error.message}")
                        selesaiCek()
                    }

                    fun selesaiCek() {
                        processedCount++
                        if (processedCount == ids.size) {
                            onComplete(namaList)
                        }
                    }
                })
        }
    }

    fun getIbu(uidIbu: String): Flow<List<String>> = callbackFlow {
        if (UserData.listPendamping.isNotEmpty()) {
            trySend(UserData.listPendamping).isSuccess
            close()
            return@callbackFlow
        }

        val ref = FirebaseProvider.database.child("user").child(uidIbu).child("username")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nama = snapshot.getValue(String::class.java)
                if (nama != null) {
                    UserData.listPendamping = listOf(nama)
                    trySend(UserData.listPendamping).isSuccess
                } else {
                    trySend(emptyList()).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
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

    private fun getUid(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencedKey.uidIbu]
        }
    }
}
