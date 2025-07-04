package com.example.slashcom.ui.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.UserData
import com.example.slashcom.ui.presentation.component.AuthEditText
import com.example.slashcom.ui.presentation.component.BlueButtonFull
import kotlinx.coroutines.launch

@Composable
fun VerifikasiPendampingScreen(
    navController: NavController, viewModel: AuthViewModel = viewModel()
) {
    var kodeValue by remember { mutableStateOf("") }
    val verifikasiState by viewModel.verifikasiState.collectAsState()
    val context = LocalContext.current

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
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Hubungkan Pendamping",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF0B1957),
            ), textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 24.dp)
        )

        // Description
        Text(
            text = "Masukkan ID yang ada di profil orang yang Anda dampingi. Pendamping akan menerima peringatan jika deteksi sistem menunjukkan tingkat stres tinggi dan kalimat risiko.",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Input Field
        AuthEditText(
            value = kodeValue,
            onValueChange = { kodeValue = it },
            label = "Kode",
            placeholder = "Masukkan Kode",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Verifikasi Button
        BlueButtonFull(
            text = "Verifikasi", onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.verifyAndSaveUid(context, kodeValue)
                }
            }, state = verifikasiState
        )
        when (verifikasiState) {
            is State.Success -> {
                navController.navigate("pendampingDashboard") {
                    popUpTo("Konfirmasi") { inclusive = true }
                }
                Toast.makeText(
                    context,
                    "Berhasil menghubungkan dengan sang ibu💕",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is State.Error -> {
                val message = (verifikasiState as State.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
