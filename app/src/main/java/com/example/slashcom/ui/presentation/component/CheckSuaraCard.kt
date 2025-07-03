package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import com.example.slashcom.R
@Composable
fun CheckSuaraCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(372.dp)
            .height(200.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFF472B6), Color(0xFFC084FC))
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_microphone)),
                contentDescription = "Microphone",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = "Mulai Check-In Suara",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Ceritakan apa yang kamu rasakan hari ini",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Ubah Box ini jadi tombol klik
            Box(
                modifier = Modifier
                    .width(118.dp)
                    .height(41.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(50.dp))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mulai",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFFF472B6),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewCheckSuaraCard() {
    CheckSuaraCard(onClick = {})
}
