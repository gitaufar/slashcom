package com.example.slashcom.domain.repository

import com.example.slashcom.domain.model.AudioFile

interface AudioRepository {
    suspend fun uploadAudio(audioFile: AudioFile): Boolean
}