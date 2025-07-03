package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R

@Composable
fun TingkatStresCard(
    level: Int,
    modifier: Modifier = Modifier
) {
    val levelColor = when (level) {
        in 1..4 -> Color(0xFF10B981) // Hijau
        in 5..7 -> Color(0xFFF59E0B) // Kuning
        in 8..10 -> Color(0xFFEF4444) // Merah
        else -> Color.Gray
    }

    val levelString = when (level) {
        in 1..4 -> "Rendah"
        in 5..7 -> "Sedang"
        in 8..10 -> "Tinggi"
        else -> "Tidak Diketahui"
    }

    Box(
        modifier = modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(20.dp), clip = false)
            .width(352.dp)
            .height(209.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Tingkat Stres",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color(0xFF121212),
                    textAlign = TextAlign.Center
                )
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(width = 5.dp, color = levelColor, shape = RoundedCornerShape(100.dp))
                    .width(90.dp)
                    .height(90.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = level.toString(),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "dari 10",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(115.dp)
                    .height(31.dp)
                    .background(color = Color(0xFFD1E8FF), shape = RoundedCornerShape(50.dp))
                    .padding(horizontal = 30.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = levelString,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color(0xFF193A6F),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewTingkatStresCard() {
    TingkatStresCard(level = 3)
}
