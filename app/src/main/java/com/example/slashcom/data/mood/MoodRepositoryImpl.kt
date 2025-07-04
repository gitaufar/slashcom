package com.example.slashcom.data.mood

import com.example.slashcom.data.model.MoodResponse
import com.example.slashcom.domain.repository.MoodRepository
import com.example.slashcom.utils.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MoodRepository {
    
    override suspend fun getMoodReply(audioFile: File): MoodResponse {
        val mediaType = "application/octet-stream".toMediaTypeOrNull()
        val requestBody = audioFile.asRequestBody(mediaType)
        val filePart = MultipartBody.Part.createFormData("file", audioFile.name, requestBody)
        
        return try {
            val response = apiService.getMoodReply(filePart)
            if (response.isSuccessful) {
                response.body() ?: MoodResponse(error = "Empty response body")
            } else {
                MoodResponse(error = "Error: ${response.message()}")
            }
        } catch (e: Exception) {
            MoodResponse(error = "Exception: ${e.message}")
        }
    }
}
