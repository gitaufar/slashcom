package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.slashcom.cache.UserData

@Composable
fun BottomNavBar(
    navController: NavController,
    selected: String
) {
    Box(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = Color(0xFFD3D3D3),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2.dp.toPx()
                )
            }
            .shadow(
                elevation = 6.dp,
                spotColor = Color(0x33000000),
                ambientColor = Color(0x33000000)
            )
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color.White)
            .padding(top = 5.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = R.drawable.ic_beranda,
                label = "beranda",
                isSelected = selected == "beranda",
                onClick = {
                    if (selected != "beranda") {
                        if (UserData.isIbu) navController.navigate("userDashboard") else navController.navigate(
                            "pendampingDashboard"
                        )
                    }
                }
            )
            if (UserData.isIbu) {
                NavItem(
                    icon = R.drawable.ic_riwayat,
                    label = "riwayat",
                    isSelected = selected == "riwayat",
                    onClick = { if (selected != "riwayat") navController.navigate("riwayat") }
                )
            }
            NavItem(
                icon = R.drawable.ic_profil,
                label = "profil",
                isSelected = selected == "profil",
                onClick = { navController.navigate("profil") }
            )
        }
    }
}

@Composable
fun NavItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = Color(0xFF2D82D5)
    val inactiveColor = Color(0xFFD3D3D3)
    val tintColor = if (isSelected) activeColor else inactiveColor

    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(tintColor)
            )
        }
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = tintColor,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    BottomNavBar(navController = navController, selected = "beranda")
}
