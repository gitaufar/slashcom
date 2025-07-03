package com.example.slashcom.ui.presentation.auth

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.component.BlueButtonFull
import com.example.slashcom.ui.presentation.component.RoleCard

@Composable
fun PilihRole(
    navController: NavController
){
    var isIbu by remember { mutableStateOf(true) }
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
            .padding(horizontal = 30.dp, vertical = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Kamu masuk sebagai...",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                RoleCard(painterResource = painterResource(R.drawable.role_ibu), role = "Ibu", isClick = isIbu, modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isIbu = true
                    }
                )
                RoleCard(painterResource = painterResource(R.drawable.role_bapak), role = "Pendamping", isClick = !isIbu, modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isIbu = false
                    }
                )
            }
        }
        BlueButtonFull(onClick = {
            navController.navigate("register?isIbu=$isIbu")
        }, text = "Lanjutkan")
    }
}