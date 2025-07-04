package com.example.slashcom.ui.presentation.pendamping.dashboard

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.component.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("UseOfNonLambdaOffsetOverload")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PendampingDashboardScreen(
    navController: NavController,
    viewModel: PendampingDashboardViewModel = remember { PendampingDashboardViewModel() }
) {
    val lastMood by viewModel.lastMood.collectAsState()
    val listMood by viewModel.listMood.collectAsState()
    val context = LocalContext.current
    var activeTab by remember { mutableStateOf("daruratTerkini") }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    var tabWidth by remember { mutableStateOf(0.dp) }

    val indicatorOffset by animateDpAsState(
        targetValue = if (activeTab == "daruratTerkini") 0.dp else tabWidth, label = ""
    )

    LaunchedEffect(UserData.lastMood) {
        viewModel.loadLastMood(context)
        viewModel.loadListMood(context)
    }

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
                    text = "Ayo pantau bagaimana perkembangan emosional sang ibu",
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
                Column {
                    BoxWithConstraints {
                        val totalWidth = maxWidth
                        tabWidth = totalWidth / 2

                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { activeTab = "daruratTerkini" }
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Darurat Terkini",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                            color = if (activeTab == "daruratTerkini") Color(
                                                0xFF0B1957
                                            ) else Color(0xFF7F7F7F),
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { activeTab = "riwayatEmosi" }
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Riwayat Emosi",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                            color = if (activeTab == "riwayatEmosi") Color(
                                                0xFF0B1957
                                            ) else Color(0xFF7F7F7F),
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }

                            // Garis background abu-abu dan indikator aktif
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(Color(0xFFE5E7EB))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .offset(x = indicatorOffset)
                                        .width(tabWidth)
                                        .height(2.dp)
                                        .background(Color(0xFF0B1957))
                                )
                            }
                        }
                    }
                }

                // Konten tab aktif
                if (activeTab == "riwayatEmosi") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        listMood.reversed().forEach { item ->
                            RiwayatEmosiCard(
                                emosi = item.emosi,
                                tanggal = LocalDate.parse(item.date, formatter),
                                tingkatStres = item.stress,
                                onClick = { /* Handle click */ }
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        listMood.filter { it.crisis }.forEach { item ->
                            RiwayatDaruratCard(
                                judul = "Ibu merasa ${item.emosi}",
                                tanggal = LocalDate.parse(item.date, formatter),
                                waktu = LocalDateTime.parse(item.date, formatter),
                                onClick = { /* Handle click */ }
                            )
                        }
                    }
                }

                // Tombol Bantuan
                BantuanButton(
                    text = "Bantuan Darurat",
                    iconResId = R.drawable.ic_telephone,
                    onClick = {}
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PendampingDashboardScreenPreview() {
    val navController = rememberNavController()
    PendampingDashboardScreen(navController = navController)
}