package com.example.slashcom.data.model

class MoodResponse (
    val filename: String = "",
    val predicted_emotion: String = "",
    val predicted_stress_level: Float = 0f,
    val error: String? = null
)