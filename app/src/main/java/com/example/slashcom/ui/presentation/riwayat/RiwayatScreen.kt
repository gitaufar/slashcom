package com.example.slashcom.ui.presentation.riwayat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.component.BottomNavBar
import com.example.slashcom.ui.presentation.component.RiwayatEmosiCard
import java.time.LocalDate

data class RiwayatEmosi(
    val emosi: String,
    val tanggal: LocalDate,
    val tingkatStres: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RiwayatScreen(navController: NavController) {
    val riwayatList = listOf(
        RiwayatEmosi("normal", LocalDate.of(2025, 7, 3), 3),
        RiwayatEmosi("sedih", LocalDate.of(2025, 7, 2), 5),
        RiwayatEmosi("lelah", LocalDate.of(2025, 7, 1), 6),
        RiwayatEmosi("marah", LocalDate.of(2025, 6, 30), 8),
        RiwayatEmosi("cemas", LocalDate.of(2025, 6, 29), 7),
        RiwayatEmosi("lelah", LocalDate.of(2025, 6, 28), 6),
        RiwayatEmosi("sedih", LocalDate.of(2025, 6, 27), 5)
    )

    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        bottomBar = {
            BottomNavBar(
                navController = navController,
                selected = "riwayat"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Riwayat",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            color = Color(0xFF121212),
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .padding(bottom = 600.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    ) {
                        Text(
                            text = "Timeline Emosi",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                color = Color(0xFF121212),
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        riwayatList.forEach { item ->
                            RiwayatEmosiCard(
                                emosi = item.emosi,
                                tanggal = item.tanggal,
                                tingkatStres = item.tingkatStres,
                                onClick = {}
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}