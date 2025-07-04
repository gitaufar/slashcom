package com.example.slashcom.domain.repository

import com.example.slashcom.data.model.CekCrisisResponse
import java.io.File

interface UploadAudioRepository {
    suspend fun uploadAudio(file: File): CekCrisisResponse?
}
