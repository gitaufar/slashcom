package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.slashcom.R

@Composable
fun ProfilScreen(
    fullName: String,
    userId: String,
    username: String,
    email: String,
    companionName: String,
    navController: NavController,
    onEditClick: () -> Unit,
    onDeleteCompanionClick: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        bottomBar = {
            BottomNavBar(
                navController = navController,
                selected = "profil"
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
                        .padding(top = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Profil",
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
                        .padding(bottom = 320.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar & Nama
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(Color(0xFFF472B6), Color(0xFFC084FC))
                                        ),
                                        shape = RoundedCornerShape(100.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = fullName.firstOrNull()?.uppercase() ?: "?",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        lineHeight = 28.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = fullName,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 28.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color(0xFF121212),
                                        textAlign = TextAlign.Center
                                    )
                                )
                                Text(
                                    text = "ID: $userId",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 28.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFF7F7F7F),
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }

                        // Info Akun Card
                        InfoAkunCard(
                            username = username,
                            email = email,
                            onEditClick = onEditClick
                        )

                        // Pendamping Card
                        PendampingCard(
                            name = companionName,
                            onDeleteClick = onDeleteCompanionClick
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewProfilScreen() {
//    ProfilScreen(
//        fullName = "Safira Rahma",
//        userId = "849349",
//        username = "safirarhm",
//        email = "safirarhm@gmail.com",
//        companionName = "Adi Wijaya",
//        navController = rememberNavController(),
//        onEditClick = {},
//        onDeleteCompanionClick = {}
//    )
//}
