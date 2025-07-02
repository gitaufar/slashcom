package com.example.slashcom.cache

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencedKey {
    val IS_ONBOARDING_SHOWN = booleanPreferencesKey("is_onboarding_shown")
}