package com.example.slashcom.data.gemini

import android.content.Context
import com.example.slashcom.BuildConfig
import com.example.slashcom.cache.UserData
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.di.GeminiProvider
import com.example.slashcom.domain.repository.GeminiRepository
import com.example.slashcom.data.model.GeminiRequest
import com.example.slashcom.data.model.GeminiResponse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GeminiRepositoryImpl(private val context: Context) : GeminiRepository {

    private val database = FirebaseProvider.database
    private val geminiKey = BuildConfig.GEMINI_API_KEY

    override suspend fun getGeminiReply(
        emosi: String,
        tingkatStress: Int,
        isCrisis: Boolean
    ): Pair<String, Boolean> {
        return try {
            val finalPrompt = buildString {
                appendLine("Bayangkan Anda sedang membantu seorang ibu yang baru saja melahirkan. " + "Saat ini ia merasa $emosi secara emosional dengan tingkat stres $tingkatStress dari 10 " + if (isCrisis) "dan fase krisis." else ".")
                appendLine("Tolong berikan 5 langkah kecil dan realistis yang bisa langsung ia lakukan saat ini untuk menenangkan diri.")
                appendLine("Gunakan bahasa yang lembut dan penuh empati.")
                appendLine("Jelaskan hanya langkah tersebut.")
                appendLine("Tidak perlu menyapa atau memberi kalimat pembuka maupun penutup.")
                appendLine("Langsung berikan langkah dan penjelasannya saja, tanpa tambahan apapun.")
            }

            val request = GeminiRequest(
                contents = listOf(
                    GeminiRequest.Content(
                        parts = listOf(
                            GeminiRequest.Part(text = finalPrompt)
                        )
                    )
                )
            )

            val response: GeminiResponse = GeminiProvider.service.sendPrompt(
                apiKey = geminiKey,
                request = request
            )

            val replyText = response.candidates?.firstOrNull()
                ?.content?.parts?.firstOrNull()?.text ?: "No response"

            val uid = UserData.uid
            val saranRef = database.child("ibu").child(uid).child("saran")
            saranRef.push().setValue(replyText)

            Pair(replyText, true)
        } catch (e: Exception) {
            Pair("Error: ${e.message}", false)
        }
    }

    override suspend fun getRandomSaran(): String {
        return suspendCancellableCoroutine { cont ->
            val uid = UserData.uid
            val saranRef = database.child("ibu").child(uid).child("saran")

            saranRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<String>()
                    for (child in snapshot.children) {
                        val value = child.getValue(String::class.java)
                        if (!value.isNullOrBlank()) {
                            list.add(value)
                        }
                    }

                    if (list.isNotEmpty()) {
                        val random = list.random()
                        cont.resume(random)
                    } else {
                        cont.resume("Belum ada saran yang tersedia.")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    cont.resume("Gagal mengambil data: ${error.message}")
                }
            })
        }
    }
}
