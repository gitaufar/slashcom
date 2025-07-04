package com.example.slashcom.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiMoodConfig {
    companion object {
        val BASE_URL = "http://192.168.1.4:8000/"
    }
    
    fun getApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}