package com.example.slashcom.ui.presentation.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.splash.SplashViewModel
import kotlinx.coroutines.launch

@Composable
fun Onboarding2(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Color(0xFFFFF8E1), // Cream dari atas
                    0.2f to Color(0xFFFFF8E1), // Cream lebih panjang
                    1.0f to Color(0xFFB3E5FC)  // Light Blue di bawah
                )
            )
        )
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterVertically)
    ) {
        Image(
            painter = painterResource(R.drawable.ilustrasi_onboard2),
            contentDescription = "Logo"
        )
        Text(
            text = "Lorem ipsum dolor sit amet",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            modifier = Modifier
                .width(332.dp)
                .height(112.dp),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontWeight = FontWeight(400),
                color = Color(0xFF555555),
                textAlign = TextAlign.Center,
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.clickable {
                    navController.navigate("onboard");
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Image(painterResource(R.drawable.arrow_left), contentDescription = "")
                Text(
                    text = "Kembali",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF0B1957),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Row(
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        viewModel.setOnboardingShown(context, true)

                        navController.navigate("getStarted") {
                            popUpTo("onboard2") { inclusive = true }
                        }
                    }
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Selanjutnya",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF0B1957),
                        textAlign = TextAlign.Center,
                    )
                )
                Image(painterResource(R.drawable.arrow_right), contentDescription = "")
            }
        }
    }
}