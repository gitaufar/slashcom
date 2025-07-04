package com.example.slashcom.ui.presentation.profil

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.UserData
import com.example.slashcom.cache.dataStore
import com.example.slashcom.data.repository.DashboardRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
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

    fun loadIbu(context: Context) {
        viewModelScope.launch {
            val uidIbu = context.dataStore.data
                .map { preferences -> preferences[PreferencedKey.uidIbu] }
                .firstOrNull()

            if (uidIbu != null) {
                repository.getIbu(uidIbu).collect { result ->
                    _listPendamping.value = result
                }
            } else {
                Log.w("loadPendamping", "UID Ibu tidak ditemukan di DataStore")
            }
        }
    }

}
