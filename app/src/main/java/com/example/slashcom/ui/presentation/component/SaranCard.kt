package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.slashcom.R

@Composable
fun SaranCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            )
            .width(372.dp)
            .height(199.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = Color(0xFF2D82D5),
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color(0xFF7F7F7F),
                )
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewSaranCard() {
    SaranCard(
        title = "Saran Buat Ibu",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
    )
}
