package com.example.slashcom.ui.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.componen.AuthEditText
import com.example.slashcom.ui.presentation.componen.BlueButtonFull

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            .padding(horizontal = 30.dp, vertical = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "[logo]",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontWeight = FontWeight(700),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            )
            AuthEditText(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                placeholder = "Masukkan Email"
            )
            AuthEditText(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Masukkan Password",
                isPassword = true
            )
            Text(
                text = "Lupa kata sandi?",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2D82D5),
                )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            BlueButtonFull(onClick = {
                viewModel.login(email, password)
            }, text = "Login", state = loginState)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Belum punya akun? ",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF555555),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    modifier = Modifier.clickable { navController.navigate("pilihRole") },
                    text = "Register",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF2D82D5),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
    when (loginState) {
        is State.Success -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, UserData.isIbu.toString(), Toast.LENGTH_SHORT).show()
                if (UserData.isIbu) {
                    navController.navigate("recorder")
                }
                viewModel.resetLoginState()
            }
        }

        is State.Error -> {
            val message = (loginState as State.Error).message
            LaunchedEffect(message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.resetLoginState()
            }
        }

        else -> Unit
    }
}
