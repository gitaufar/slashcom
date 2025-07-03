package com.example.slashcom

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.slashcom.ui.presentation.auth.LoginScreen
import com.example.slashcom.ui.presentation.auth.PilihRole
import com.example.slashcom.ui.presentation.auth.RegisterScreen
import com.example.slashcom.ui.presentation.dashboard.DashboardScreen
import com.example.slashcom.ui.presentation.onboard.GetStarted
import com.example.slashcom.ui.presentation.onboard.Onboarding1
import com.example.slashcom.ui.presentation.onboard.Onboarding2
import com.example.slashcom.ui.presentation.recorder.RecorderScreen
import com.example.slashcom.ui.presentation.riwayat.RiwayatScreen
import com.example.slashcom.ui.presentation.splash.SplashScreen
import com.example.slashcom.ui.theme.SlashcomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SlashcomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard"
                    ) {
                        composable("splash") { SplashScreen(navController) }
                        composable("onboard") { Onboarding1(navController) }
                        composable("onboard2") { Onboarding2(navController) }
                        composable("getStarted") { GetStarted(navController) }
                        composable("login") { LoginScreen(navController = navController) }
                        composable("pilihRole") { PilihRole(navController) }
                        composable("dashboard") { DashboardScreen(navController) }
                        composable(
                            "register?isIbu={isIbu}",
                            arguments = listOf(
                                navArgument("isIbu") {
                                    type = NavType.BoolType
                                    defaultValue = false
                                }
                            )) { backStackEntry ->
                            val isIbu = backStackEntry.arguments?.getBoolean("isIbu") ?: false
                            RegisterScreen(isIbu = isIbu, navController = navController)
                        }
                        composable("dashboard") { DashboardScreen(navController) }
                        composable("riwayat") { RiwayatScreen(navController) }
                        composable("recorder") {
                            RecorderScreen(
                                modifier = Modifier.padding(
                                    innerPadding
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CheckAudioPermission() {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            ActivityCompat.requestPermissions(
                (context as ComponentActivity),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        }
    }
}