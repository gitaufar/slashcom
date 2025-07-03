package com.example.slashcom.ui.presentation.pendamping.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.component.*
import com.example.slashcom.ui.presentation.user.dashboard.DashboardViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = remember { DashboardViewModel() }
) {
    val lastMood by viewModel.lastMood.collectAsState()
    val uid = UserData.uid
    var activeTab by remember { mutableStateOf("riwayatEmosi") }

    LaunchedEffect(uid) {
        viewModel.loadLastMood(uid)
    }

    // Sample data for riwayat emosi
    val riwayatEmosi = listOf(
        RiwayatEmosiItem("Marah", LocalDate.of(2025, 7, 3), 6),
        RiwayatEmosiItem("Marah", LocalDate.of(2025, 7, 3), 6)
    )

    // Sample data for riwayat darurat
    val riwayatDarurat = listOf(
        RiwayatDaruratItem("Ibu", LocalDate.of(2025, 7, 3), "22:38"),
        RiwayatDaruratItem("Ibu", LocalDate.of(2025, 7, 3), "22:38")
    )

    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        bottomBar = {
            BottomNavBar(
                navController = navController,
                selected = "beranda"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 20.dp, end = 20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
                }
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .padding(vertical = 20.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Mood ibu terakhir kali section
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

                    // Darurat Terkini dan Riwayat Emosi section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
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
                        )
                    }
                }
            }

            // Conditional display of cards based on active tab
            if (activeTab == "riwayatEmosi") {
                items(riwayatEmosi) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                    ) {
                        RiwayatEmosiCard(
                            emosi = item.emosi,
                            tanggal = item.tanggal,
                            tingkatStres = item.tingkatStres,
                            onClick = { /* Handle click */ }
                        )
                    }
                }
            } else {
                items(riwayatDarurat) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                    ) {
                        RiwayatDaruratCard(
                            judul = item.judul,
                            tanggal = item.tanggal,
                            waktu = item.waktu,
                            onClick = { /* Handle click */ }
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    BantuanButton(
                        text = "Bantuan Darurat",
                        iconResId = R.drawable.ic_telephone
                    )
                }
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