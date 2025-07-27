package com.example.slashcom.cache

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencedKey {
    val uidIbu = stringPreferencesKey("uid_ibu")
    val IS_ONBOARDING_SHOWN = booleanPreferencesKey("is_onboarding_shown")
}