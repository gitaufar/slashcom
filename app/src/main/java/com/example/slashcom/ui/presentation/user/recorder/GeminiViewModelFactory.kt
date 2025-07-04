package com.example.slashcom.ui.presentation.user.recorder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GeminiViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeminiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GeminiViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
