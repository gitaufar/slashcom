package com.example.slashcom.ui.presentation.user.recorder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.data.gemini.GeminiRepositoryImpl
import com.example.slashcom.domain.usecase.GeminiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GeminiViewModel(context: Context) : ViewModel() {

    private val geminiUseCase = GeminiUseCase(GeminiRepositoryImpl(context))

    private val _saran = MutableStateFlow<String>("")
    val saran: StateFlow<String> = _saran

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    fun ambilSaran(emosi: String = "lelah", tingkatStress: Int = 3, isCrisis: Boolean = false) {
        viewModelScope.launch {
            _loading.value = true
            val (hasil, sukses) = geminiUseCase.getSaranGemini(emosi,tingkatStress, isCrisis)
            _saran.value = hasil
            _loading.value = false
        }
    }
}
