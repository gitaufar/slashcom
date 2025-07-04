package com.example.slashcom.data.gemini

import android.content.Context
import com.example.slashcom.BuildConfig
import com.example.slashcom.cache.UserData
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.di.GeminiProvider
import com.example.slashcom.domain.repository.GeminiRepository
import com.example.slashcom.data.model.GeminiRequest
import com.example.slashcom.data.model.GeminiResponse

class GeminiRepositoryImpl(private val context: Context) : GeminiRepository {

    private val database = FirebaseProvider.database
    private val geminiKey = BuildConfig.GEMINI_API_KEY

    override suspend fun getGeminiReply(prompt: String): Pair<String, Boolean> {
        return try {
            val finalPrompt = buildString {
                appendLine("Bayangkan Anda sedang membantu seorang ibu yang baru saja melahirkan. Saat ini ia merasa lelah secara emosional dengan tingkat stres 3 dari 10 dan fase krisis .")
                appendLine("Tolong berikan satu langkah kecil dan realistis yang bisa langsung ia lakukan saat ini untuk menenangkan diri.")
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
}
