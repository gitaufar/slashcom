package com.example.slashcom.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R

@Composable
fun SaranCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    loading: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    val cleanDescription = description
        .replace("**", "")
        .replace("*", "")
        .replace("_", "")
        .replace("```", "")
        .replace("#", "")

    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            )
            .width(372.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 199.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = Color(0xFF2D82D5),
                    textAlign = TextAlign.Start
                )
            )

            if (!loading) {
                val maxLines = if (expanded) Int.MAX_VALUE else 4

                Text(
                    text = cleanDescription,
                    maxLines = maxLines,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFF7F7F7F),
                    )
                )

                if (cleanDescription.length > 150) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (expanded) "Lihat Sedikit" else "Selengkapnya",
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .padding(top = 4.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = Color(0xFF2D82D5)
                        )
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
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
