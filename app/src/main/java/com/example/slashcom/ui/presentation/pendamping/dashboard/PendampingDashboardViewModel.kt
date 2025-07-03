package com.example.slashcom.ui.presentation.pendamping.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.data.repository.DashboardRepositoryImpl
import com.example.slashcom.domain.model.Mood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PendampingDashboardViewModel(
    private val repository: DashboardRepositoryImpl = DashboardRepositoryImpl()
) : ViewModel() {
    private val _lastMood = MutableStateFlow<Mood?>(null)
    val lastMood: StateFlow<Mood?> = _lastMood

    fun loadLastMood(context: Context) {
        viewModelScope.launch {
            repository.getLastMoodFromAyah(context)
                .catch { e -> e.printStackTrace() }
                .collect { mood -> _lastMood.value = mood }
        }
    }
}