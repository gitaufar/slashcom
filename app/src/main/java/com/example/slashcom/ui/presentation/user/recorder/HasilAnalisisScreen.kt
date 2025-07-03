package com.example.slashcom.ui.presentation.user.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
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
import androidx.navigation.compose.rememberNavController
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.component.*

@Composable
fun HasilAnalisisScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFF8E1),
                        Color(0xFFB3E5FC)
                    )
                )
            )
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(start = 20.dp, top = 40.dp, end = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Analisis Emosi dan Stres",
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.width(30.dp)) // Placeholder agar teks tetap di tengah
        }

        Divider(
            color = Color(0xFFCCCCCC),
            thickness = 0.5.dp,
            modifier = Modifier.fillMaxWidth()
        )

        // Konten
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            EmosiTerdeteksiCard(emosi = "Lelah")
            TingkatStresCard(level = 6)
            BantuanButton(
                text = "Fase Kritis Terdeteksi",
                iconResId = R.drawable.ic_alert
            )
            SaranCard(
                title = "Saran Buat Ibu",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
            )

            BlueButtonFull(
                text = "Selesai",
                onClick = { navController.navigate("dashboard") }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHasilAnalisisScreen() {
    val navController = rememberNavController()
    HasilAnalisisScreen(navController)
}