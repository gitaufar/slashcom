package com.example.slashcom.ui.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.di.FirebaseProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    val context = LocalContext.current
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(500))
        delay(700L)
        alpha.animateTo(0f, animationSpec = tween(300))

        val currentUser = FirebaseProvider.auth.currentUser
        if (currentUser != null) {
            val isIbu = viewModel.checkUserIsIbu(currentUser.uid)
            if(isIbu) {
                navController.navigate("userDashboard") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navController.navigate("pendampingDashboard") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        } else {
            val isOnboardingShown = viewModel.isOnboardingShown(context).first()
            if (isOnboardingShown) {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navController.navigate("onboard") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFFFFF8E1),
                        0.2f to Color(0xFFFFF8E1),
                        1.0f to Color(0xFFB3E5FC)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_ibu),
            contentDescription = "Logo",
            modifier = Modifier
                .size(300.dp)
                .alpha(alpha.value)
        )
    }
}
