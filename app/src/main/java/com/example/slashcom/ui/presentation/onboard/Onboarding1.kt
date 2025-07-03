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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R

@Composable
fun Onboarding1(
    navController: NavController,
) {
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
            painter = painterResource(R.drawable.ilustrasi_onboard1),
            contentDescription = "Logo"
        )
        Text(
            text = "Ibu mencurahkan, rasa diterima",
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
            text = "Swara Ibu hadir mendampingi untuk mendengar, memahami, dan merangkul emosi pasca persalinan Ibu dengan penuh kasih.",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontWeight = FontWeight(400),
                color = Color(0xFF555555),
                textAlign = TextAlign.Center,
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.clickable {
                    navController.navigate("onboard2");
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