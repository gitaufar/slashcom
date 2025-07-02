package com.example.slashcom.domain.usecase

import com.example.slashcom.domain.model.AudioFile
import com.example.slashcom.domain.repository.AudioRepository

class UploadAudioUseCase(private val repository: AudioRepository) {
    suspend fun execute(audioFile: AudioFile): Boolean {
        return repository.uploadAudio(audioFile)
    }
}