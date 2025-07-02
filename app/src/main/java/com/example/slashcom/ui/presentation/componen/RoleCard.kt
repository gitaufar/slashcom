package com.example.slashcom.ui.presentation.componen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R

@Composable
fun RoleCard(
    painterResource: Painter,
    role: String,
    isClick: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 15.dp))
            .border(
                width = if (isClick) 2.dp else 0.dp,
                color = if (isClick) Color(0xFF0B1957) else Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(top = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(126.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource,
                contentDescription = "role image",
                modifier = if (role == "Ibu") {
                    Modifier.fillMaxHeight()
                } else {
                    Modifier.fillMaxWidth()
                },
                contentScale = if (role == "Ibu") ContentScale.FillHeight else ContentScale.FillWidth
            )
        }
        Text(
            text = role,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF0B1957),
                textAlign = TextAlign.Center,
            )
        )
    }
}
