package com.example.slashcom.ui.presentation.profil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.cache.UserData
import com.example.slashcom.data.repository.DashboardRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfilViewModel(
    private val repository: DashboardRepositoryImpl = DashboardRepositoryImpl()
) : ViewModel() {

    private val _listPendamping = MutableStateFlow(UserData.listPendamping)
    val listPendamping: StateFlow<List<String>> = _listPendamping

    fun loadPendamping(uid: String) {
        viewModelScope.launch {
            repository.getPendamping(uid).collect { result ->
                _listPendamping.value = result
            }
        }
    }
}
