package com.example.slashcom.ui.presentation.splash

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import com.example.slashcom.cache.PreferencedKey
import com.example.slashcom.cache.UserData
import com.example.slashcom.cache.dataStore
import com.example.slashcom.di.FirebaseProvider
import com.example.slashcom.domain.model.IbuData
import com.example.slashcom.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class SplashViewModel: ViewModel() {

    fun isOnboardingShown(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferencedKey.IS_ONBOARDING_SHOWN] ?: false
            }
    }

    suspend fun checkUserIsIbu(uid: String): Boolean {
        val snapshot = FirebaseProvider.database.child("user").child(uid).get().await()

        if (snapshot.exists()) {
            val userData = snapshot.getValue(User::class.java)
            UserData.userName = userData?.username ?: ""
            UserData.uid = uid
            UserData.isIbu = userData?.isIbu ?: false
            UserData.email = userData?.email ?: ""

            val ibuSnapshot = FirebaseProvider.database.child("ibu").child(uid).get().await()
            if (ibuSnapshot.exists()) {
                val ibuData = ibuSnapshot.getValue(IbuData::class.java)
                UserData.id = ibuData?.id ?: "kosong idnya"
            }

            return UserData.isIbu
        }

        return false
    }


    suspend fun setOnboardingShown(context: Context, shown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencedKey.IS_ONBOARDING_SHOWN] = shown
        }
    }

}