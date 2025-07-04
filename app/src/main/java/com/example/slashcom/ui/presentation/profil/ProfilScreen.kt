package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.profil.ProfilViewModel

@Composable
fun ProfilScreen(
    navController: NavController,
    onEditClick: () -> Unit,
    onDeleteCompanionClick: () -> Unit,
    viewModel: ProfilViewModel = remember { ProfilViewModel() }
) {
    val listPendamping by viewModel.listPendamping.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPendamping(UserData.uid)
    }

    val fullName = UserData.userName
    val userId = UserData.id
    val username = UserData.userName
    val email = UserData.email

    Scaffold(
        containerColor = Color(0xFFD1E8FF),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 24.dp, bottom = 20.dp),
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
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                selected = "profil"
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    // Avatar & Nama
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
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
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    color = Color.White
                                )
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = fullName,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    color = Color(0xFF121212)
                                )
                            )
                            Text(
                                text = if (UserData.isIbu) "ID: $userId" else "-",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    color = Color(0xFF7F7F7F)
                                )
                            )
                        }
                    }
                }

                item {
                    InfoAkunCard(username = username, email = email, onEditClick = onEditClick)
                }

                items(listPendamping) { name ->
                    PendampingCard(name = name, onDeleteClick = onDeleteCompanionClick)
                }

                item {
                    BantuanButton(
                        text = "Logout",
                        iconResId = R.drawable.ic_logout,
                        onClick = {
                            UserData.userName = ""
                            UserData.id = ""
                            UserData.email = ""
                            UserData.isIbu = false
                            UserData.lastMood = null
                            UserData.listMoods = null

                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
