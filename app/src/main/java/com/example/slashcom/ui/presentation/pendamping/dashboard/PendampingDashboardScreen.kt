package com.example.slashcom.ui.presentation.pendamping.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.component.*
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PendampingDashboardScreen(
    navController: NavController,
    viewModel: PendampingDashboardViewModel = remember { PendampingDashboardViewModel() }
) {
    val lastMood by viewModel.lastMood.collectAsState()
    val context = LocalContext.current
    var activeTab by remember { mutableStateOf("riwayatEmosi") }

    LaunchedEffect(context) {
        viewModel.loadLastMood(context)
    }

    val riwayatEmosi = listOf(
        RiwayatEmosiItem("Marah", LocalDate.of(2025, 7, 3), 6),
        RiwayatEmosiItem("Cemas", LocalDate.of(2025, 7, 2), 4)
    )

    val riwayatDarurat = listOf(
        RiwayatDaruratItem("Ibu butuh bantuan", LocalDate.of(2025, 7, 3), "22:38"),
        RiwayatDaruratItem("Ibu merasa panik", LocalDate.of(2025, 7, 2), "21:15")
    )

    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        bottomBar = {
            BottomNavBar(navController = navController, selected = "beranda")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Sapaan
            Column(
                modifier = Modifier.padding(top = 24.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Halo, ${UserData.userName}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = Color.Black
                    )
                )
                Text(
                    text = "Ayo pantau bagaimana perkembangan emosional sang ibu❤️",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Konten utama dengan background putih
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Mood ibu
                Text(
                    text = "Mood ibu terakhir kali",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = Color.Black
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    EmosiIbuCard(
                        emosi = lastMood?.emosi ?: "normal",
                        modifier = Modifier.weight(1f)
                    )
                    LevelStressCard(
                        level = lastMood?.stress ?: 3,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Tab antara Darurat Terkini dan Riwayat Emosi
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Darurat Terkini",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            color = Color.Black,
                            textDecoration = if (activeTab == "daruratTerkini") TextDecoration.Underline else TextDecoration.None
                        ),
                        modifier = Modifier
                            .clickable { activeTab = "daruratTerkini" }
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    Text(
                        text = "Riwayat Emosi",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            color = Color.Black,
                            textDecoration = if (activeTab == "riwayatEmosi") TextDecoration.Underline else TextDecoration.None
                        ),
                        modifier = Modifier
                            .clickable { activeTab = "riwayatEmosi" }
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }

                // Konten tab aktif
                if (activeTab == "riwayatEmosi") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        riwayatEmosi.forEach { item ->
                            RiwayatEmosiCard(
                                emosi = item.emosi,
                                tanggal = item.tanggal,
                                tingkatStres = item.tingkatStres,
                                onClick = { /* Handle click */ }
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        riwayatDarurat.forEach { item ->
                            RiwayatDaruratCard(
                                judul = item.judul,
                                tanggal = item.tanggal,
                                waktu = item.waktu,
                                onClick = { /* Handle click */ }
                            )
                        }
                    }
                }

                // Tombol Bantuan
                BantuanButton(
                    text = "Bantuan Darurat",
                    iconResId = R.drawable.ic_telephone
                )
            }
        }
    }
}


data class RiwayatEmosiItem(
    val emosi: String,
    val tanggal: LocalDate,
    val tingkatStres: Int
)

data class RiwayatDaruratItem(
    val judul: String,
    val tanggal: LocalDate,
    val waktu: String
)