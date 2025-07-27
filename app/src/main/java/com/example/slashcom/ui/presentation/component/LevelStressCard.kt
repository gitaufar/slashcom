package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import com.example.slashcom.R

@Composable
fun LevelStressCard(
    level: Int,
    modifier: Modifier = Modifier
) {
    val levelColor = when (level) {
        in 1..4 -> Color(0xFF10B981)  // Hijau
        in 5..7 -> Color(0xFFF59E0B)  // Kuning
        in 8..10 -> Color(0xFFEF4444) // Merah
        else -> Color.Gray
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            )
            .width(176.dp)
            .height(106.dp)
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Level Stress",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = level.toString(),
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = levelColor,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "dari 10",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color(0xFF7F7F7F),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLevelStressCard() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LevelStressCard(level = 3)
        LevelStressCard(level = 6)
        LevelStressCard(level = 9)
    }
}
