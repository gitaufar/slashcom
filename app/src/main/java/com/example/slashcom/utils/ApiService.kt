package com.example.slashcom.utils

import com.example.slashcom.data.model.MoodResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/predict/")
    suspend fun getMoodReply(
        @Part file: MultipartBody.Part
    ): Response<MoodResponse>
}