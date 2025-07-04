package com.example.slashcom.ui.presentation.user.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.component.*

//@Preview
@Composable
fun UserDashboardScreen(navController: NavController, viewModel: DashboardViewModel = remember { DashboardViewModel() }) {

    val lastMood by viewModel.lastMood.collectAsState()
    val uid = UserData.uid

    LaunchedEffect(UserData.lastMood) {
        viewModel.loadLastMood(uid)
    }

    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        bottomBar = {
            BottomNavBar(
                navController = navController,
                selected = "beranda"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        text = "Halo, Ibu ${UserData.userName}",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "Bagaimana kabar ibu hari ini?",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color.Black
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
                    .padding(vertical = 20.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    EmosiIbuCard(emosi = lastMood?.emosi ?: "normal", modifier = Modifier.weight(1f))
                    LevelStressCard(level = lastMood?.stress ?: 3, modifier = Modifier.weight(1f))
                }

                SaranCard(
                    title = "Saran Buat Ibu",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
                )

                CheckSuaraCard(onClick = {
                    navController.navigate("recorder")
                })

                BantuanButton(
                    text = "Bantuan Darurat",
                    iconResId = R.drawable.ic_telephone,
                    onClick = {}
                )
            }
        }
    }
}
