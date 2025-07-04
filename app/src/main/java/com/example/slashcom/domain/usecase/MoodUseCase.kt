package com.example.slashcom.domain.usecase

import com.example.slashcom.data.model.MoodResponse
import com.example.slashcom.domain.repository.MoodRepository
import java.io.File
import javax.inject.Inject

class MoodUseCase @Inject constructor(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(audioFile: File): MoodResponse {
        return repository.getMoodReply(audioFile)
    }
}