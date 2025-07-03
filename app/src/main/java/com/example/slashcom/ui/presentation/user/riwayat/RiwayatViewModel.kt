package com.example.slashcom.ui.presentation.user.riwayat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.data.repository.DashboardRepositoryImpl
import com.example.slashcom.domain.model.Mood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RiwayatViewModel(
    private val repository: DashboardRepositoryImpl = DashboardRepositoryImpl()
) :
    ViewModel() {
    private val _moodList = MutableStateFlow<List<Mood>>(emptyList())
    val moodList: StateFlow<List<Mood>> = _moodList

    fun loadMoods(uid: String) {
        viewModelScope.launch {
            repository.getMoods(uid)
                .catch { e -> e.printStackTrace() }
                .collect { moods -> _moodList.value = moods }
        }
    }
}