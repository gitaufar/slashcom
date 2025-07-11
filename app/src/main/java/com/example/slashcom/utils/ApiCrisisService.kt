package com.example.slashcom.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCrisisService {
    private const val BASE_URL = "http://192.168.56.1:8000/"

    val instance: ApiCrisisConfig by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCrisisConfig::class.java)
    }
}