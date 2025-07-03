package com.example.slashcom.di

import com.example.slashcom.data.model.GeminiRequest
import com.example.slashcom.data.model.GeminiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

object GeminiProvider {

    interface Service {
        @POST("v1beta/models/gemini-2.0-flash:generateContent")
        suspend fun sendPrompt(
            @Query("key") apiKey: String,
            @Body request: GeminiRequest
        ): GeminiResponse
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: Service = retrofit.create(Service::class.java)
}
