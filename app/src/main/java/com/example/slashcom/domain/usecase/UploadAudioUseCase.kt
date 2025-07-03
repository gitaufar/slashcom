package com.example.slashcom.domain.usecase

import com.example.slashcom.data.model.CekCrisisResponse
import com.example.slashcom.domain.repository.UploadAudioRepository
import java.io.File

class UploadAudioUseCase(private val repository: UploadAudioRepository) {
    suspend operator fun invoke(file: File): CekCrisisResponse? {
        return repository.uploadAudio(file)
    }
}
