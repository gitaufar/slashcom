package com.example.slashcom.data.gemini

import android.content.Context
import com.example.slashcom.BuildConfig
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
                appendLine("Kamu adalah pendamping emosional digital untuk seorang ibu yang baru melahirkan. Dia sedang mengalami emosi cemas dan lelah dengan tingkat stress 5/10.\n")
                appendLine("Buatlah respons yang terdiri dari:\n")
                appendLine("- Sapaan hangat dan empatik, seolah kamu teman dekatnya.\n")
                appendLine("- Validasi emosinya tanpa menghakimi.\n")
                appendLine("- Langkah-langkah sederhana dan realistis (3–5 langkah) yang bisa dilakukan ibu saat ini untuk menenangkan diri.\n")
                appendLine("- Afirmasi positif bahwa ia tidak sendirian dan berhak mendapat dukungan.\n")
                appendLine("- Gunakan bahasa yang lembut, suportif, dan membangun kedekatan, seperti layaknya seorang teman baik yang memahami perjuangannya.\"")
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

            Pair(replyText, true)
        } catch (e: Exception) {
            Pair("Error: ${e.message}", false)
        }
    }
}
