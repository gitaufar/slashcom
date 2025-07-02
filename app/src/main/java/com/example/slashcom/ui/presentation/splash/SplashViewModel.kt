package com.example.slashcom.ui.presentation.splash

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SplashViewModel: ViewModel() {

    fun isOnboardingShown(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferencedKey.IS_ONBOARDING_SHOWN] ?: false
            }
    }

    suspend fun setOnboardingShown(context: Context, shown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencedKey.IS_ONBOARDING_SHOWN] = shown
        }
    }

}