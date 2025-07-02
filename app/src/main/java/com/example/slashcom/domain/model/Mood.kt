package com.example.slashcom.domain.model

data class Mood(
    val crisis: Boolean = false,
    val date: String = "",
    val emosi: String = "",
    val stress: Int = 0
)