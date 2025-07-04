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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
fun AlertBantuan(
    title: String,
    description: String,
    time: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(389.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFEF4444)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_alert),
                    contentDescription = "Alert Icon",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color(0xFF121212),
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center
                )
            )

            Box(
                modifier = Modifier
                    .width(260.dp)
                    .height(47.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEF4444))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Saya Mengerti",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 27.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Text(
                text = time,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun AlertBantuanPreview() {
    AlertBantuan(
        title = "Ibu Butuh Bantuan Sekarang",
        description = "Fase Kritis Terdeteksi",
        time = "Baru saja - 14:23",
        onDismiss = {}
    )
}
