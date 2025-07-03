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
            val request = GeminiRequest(
                contents = listOf(
                    GeminiRequest.Content(
                        parts = listOf(
                            GeminiRequest.Part(text = prompt)
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
