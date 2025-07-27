package com.example.slashcom.data.model

import okhttp3.MultipartBody

data class MoodRequest(
    val file: MultipartBody.Part
)