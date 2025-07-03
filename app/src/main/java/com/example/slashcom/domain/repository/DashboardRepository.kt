package com.example.slashcom.domain.repository

import com.example.slashcom.domain.model.Mood
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getMoods(uid: String): Flow<List<Mood>>
    fun getLastMood(uid: String): Flow<Mood?>
    fun addMood(mood: Mood): Unit
}