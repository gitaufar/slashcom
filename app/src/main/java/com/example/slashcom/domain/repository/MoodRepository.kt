package com.example.slashcom.domain.repository

import com.example.slashcom.data.model.MoodResponse
import java.io.File

interface MoodRepository {
    suspend fun getMoodReply(audioFile: File): MoodResponse
}