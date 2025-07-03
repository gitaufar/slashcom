package com.example.slashcom.data.api

import android.util.Log
import com.example.slashcom.data.model.CekCrisisResponse
import com.example.slashcom.domain.repository.UploadAudioRepository
import com.example.slashcom.utils.ApiCrisisConfig
import com.example.slashcom.utils.ApiCrisisService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadAudioRepositoryImpl(
    private val api: ApiCrisisConfig = ApiCrisisService.instance
) : UploadAudioRepository {

    override suspend fun uploadAudio(file: File): CekCrisisResponse? {
        val requestBody = file.asRequestBody("audio/mp4".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            name = "file",
            filename = file.name,
            body = requestBody
        )

        return try {
            val response = api.cekKrisis(multipartBody)
            if (response.isSuccessful) response.body()
            else null
        } catch (e: Exception) {
            Log.e("UploadAudio", "Upload gagal: ${e.message}")
            null
        }
    }
}
