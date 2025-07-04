package com.example.slashcom.ui.presentation.pendamping.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.slashcom.cache.UserData
import com.example.slashcom.data.repository.DashboardRepositoryImpl
import com.example.slashcom.domain.model.Mood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PendampingDashboardViewModel(
    private val repository: DashboardRepositoryImpl = DashboardRepositoryImpl()
) : ViewModel() {
    private val _lastMood = MutableStateFlow<Mood?>(UserData.lastMood)
    val lastMood: StateFlow<Mood?> = _lastMood
    private val _listMood = MutableStateFlow<List<Mood>>(emptyList())
    val listMood: StateFlow<List<Mood>> = _listMood

    fun loadLastMood(context: Context) {
        viewModelScope.launch {
            repository.getLastMoodFromAyah(context)
                .catch { e -> e.printStackTrace() }
                .collect { mood -> _lastMood.value = mood }
        }
    }

    fun loadListMood(context: Context){
        viewModelScope.launch {
            repository.getMoodsFromAyah(context)
                .catch { e -> e.printStackTrace() }
                .collect { listMood -> _listMood.value = listMood }
        }
    }

    fun loadCrisisMood(context: Context){
        viewModelScope.launch {
            repository.getMoodsFromAyah(context)
                .catch { e -> e.printStackTrace() }
                .collect { listMood -> _listMood.value = listMood }
        }
    }
}