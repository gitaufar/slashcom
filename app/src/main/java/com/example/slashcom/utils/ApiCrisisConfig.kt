package com.example.slashcom.utils

import com.example.slashcom.data.model.CekCrisisResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiCrisisConfig {
    @Multipart
    @POST("cek-krisis/")
    suspend fun cekKrisis(
        @Part file: MultipartBody.Part
    ): Response<CekCrisisResponse>
}
