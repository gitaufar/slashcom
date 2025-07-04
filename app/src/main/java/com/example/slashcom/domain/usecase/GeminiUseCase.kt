package com.example.slashcom.domain.usecase

import com.example.slashcom.domain.repository.GeminiRepository

class GeminiUseCase(private val repository: GeminiRepository) {

    suspend fun getSaranGemini(emosi: String, tingkatStress: Int, isCrisis: Boolean): Pair<String, Boolean> {
        return repository.getGeminiReply(emosi, tingkatStress, isCrisis)
    }
}