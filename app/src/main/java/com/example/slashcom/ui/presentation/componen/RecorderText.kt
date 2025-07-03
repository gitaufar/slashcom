package com.example.slashcom.ui.presentation.componen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.slashcom.R

@Composable
fun RecorderText(
    text: String
){
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 28.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight(400),
            color = Color(0xFF7F7F7F),
            textAlign = TextAlign.Center,
        )
    )
}