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
import androidx.compose.ui.draw.shadow
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

@Composable
fun BottomNavBar(
    navController: NavController,
    selected: String
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 6.dp,
                spotColor = Color(0x33000000),
                ambientColor = Color(0x33000000)
            )
            .fillMaxWidth()
            .height(90.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
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
                onClick = { navController.navigate("dashboard") }
            )
            NavItem(
                icon = R.drawable.ic_riwayat,
                label = "riwayat",
                isSelected = selected == "riwayat",
                onClick = { /* navController.navigate("riwayat") */ }
            )
            NavItem(
                icon = R.drawable.ic_profil,
                label = "profil",
                isSelected = selected == "profil",
                onClick = { /* navController.navigate("profil") */ }
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
    val inactiveColor = Color(0xFF7F7F7F)
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
