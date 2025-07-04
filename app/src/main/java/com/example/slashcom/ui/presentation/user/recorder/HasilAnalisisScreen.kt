package com.example.slashcom.ui.presentation.user.recorder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.component.*

@Composable
fun HasilAnalisisScreen(
    viewModel: RecorderViewModel = viewModel(),
    navController: NavController
) {

    val isKrisis = viewModel.isKrisis.value

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

        // Konten pakai LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
            item {
                EmosiTerdeteksiCard(emosi = "Lelah")
            }

            item {
                TingkatStresCard(level = 6)
            }

            if (isKrisis == true) {
                item {
                    BantuanButton(
                        text = "Fase Kritis Terdeteksi",
                        iconResId = R.drawable.ic_alert,
                        onClick = {}
                    )
                }
            }

            item {
                BantuanButton(
                    text = "Fase Krisis Terdeteksi",
                    iconResId = R.drawable.ic_alert,
                    onClick = {}
                )
            }

            item {
                SaranCard(
                    title = "Saran Buat Ibu",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
                )
            }

            item {
                BlueButtonFull(
                    text = "Selesai",
                    onClick = { navController.navigate("userDashboard") }
                )
            }
        }
    }
}
