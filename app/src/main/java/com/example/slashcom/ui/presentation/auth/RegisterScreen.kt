package com.example.slashcom.ui.presentation.auth

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.slashcom.ui.presentation.component.AuthEditText
import com.example.slashcom.ui.presentation.component.BlueButtonFull

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    isIbu: Boolean = true,
    navController: NavController
) {
    val context = LocalContext.current
    val registerState by viewModel.registerState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFFFFF8E1), // Cream dari atas
                        0.2f to Color(0xFFFFF8E1), // Cream lebih panjang
                        1.0f to Color(0xFFB3E5FC)
                    )
                )
            )
            .padding(horizontal = 30.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_ibu),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(120.dp),
                contentScale = ContentScale.FillHeight
            )
            AuthEditText(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                placeholder = "Masukkan Username"
            )
            AuthEditText(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                placeholder = "Masukkan Email",
            )
            AuthEditText(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Masukkan Password",
                isPassword = true
            )
            AuthEditText(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                placeholder = "Masukkan Password",
                isPassword = true
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            BlueButtonFull(onClick = {
                viewModel.register(email, password, confirmPassword, username, isIbu)
            }, text = "Register", state = registerState)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sudah punya akun? ",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color(0xFF555555),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    modifier = Modifier.clickable { navController.navigate("login") },
                    text = "Login",
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
    when (registerState) {
        is State.Success -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Register berhasil", Toast.LENGTH_SHORT).show()
                navController.navigate("login")
                viewModel.resetRegisterState()
            }
        }

        is State.Error -> {
            val message = (registerState as State.Error).message
            LaunchedEffect(message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.resetRegisterState()
            }
        }

        else -> Unit
    }
}
