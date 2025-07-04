package com.example.slashcom.domain.repository

interface GeminiRepository {
    suspend fun getGeminiReply(emosi: String, tingkatStress: Int, isCrisis: Boolean): Pair<String, Boolean>
}