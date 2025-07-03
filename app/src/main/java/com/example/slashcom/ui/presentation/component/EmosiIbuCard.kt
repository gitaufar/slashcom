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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.slashcom.R

@Composable
fun EmosiIbuCard(
    emosi: String,
    modifier: Modifier = Modifier
) {
    val imageResId = when (emosi.lowercase()) {
        "cemas" -> R.drawable.em_cemas
        "lelah" -> R.drawable.em_lelah
        "marah" -> R.drawable.em_marah
        "normal" -> R.drawable.em_normal
        "sedih" -> R.drawable.em_sedih
        else -> R.drawable.em_normal // fallback
    }

    val textColor = when (emosi.lowercase()) {
        "normal" -> Color(0xFF16A34A)
        "marah" -> Color(0xFFEF4444)
        else -> Color(0xFFF59E0B)
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            )
            .width(176.dp)
            .height(103.dp)
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Emosi Ibu",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = emosi,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = emosi.replaceFirstChar { it.uppercase() },
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = textColor
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmosiIbuCard() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        EmosiIbuCard(emosi = "normal")
        EmosiIbuCard(emosi = "cemas")
        EmosiIbuCard(emosi = "lelah")
        EmosiIbuCard(emosi = "marah")
        EmosiIbuCard(emosi = "sedih")
    }
}

