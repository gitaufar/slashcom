package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.slashcom.R

@Composable
fun EmosiTerdeteksiCard(
    emosi: String,
    modifier: Modifier = Modifier
) {
    val imageResId = when (emosi.lowercase()) {
        "cemas" -> R.drawable.em_cemas
        "lelah" -> R.drawable.em_lelah
        "marah" -> R.drawable.em_marah
        "normal" -> R.drawable.em_normal
        "sedih" -> R.drawable.em_sedih
        else -> R.drawable.em_normal
    }

    Box(
        modifier = modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(20.dp), clip = false)
            .width(352.dp)
            .height(159.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = emosi,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(48.dp)
                    .height(40.dp)
            )
            Text(
                text = "Emosi Terdeteksi",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFD1E8FF), shape = RoundedCornerShape(50.dp))
                    .padding(horizontal = 30.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emosi.replaceFirstChar { it.uppercase() },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color(0xFF0B1957),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewEmosiTerdeteksiCard() {
    EmosiTerdeteksiCard(emosi = "normal")
}
