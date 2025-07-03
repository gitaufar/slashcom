package com.example.slashcom.domain.repository

interface GeminiRepository {
    suspend fun getGeminiReply(prompt: String): Pair<String, Boolean>
}