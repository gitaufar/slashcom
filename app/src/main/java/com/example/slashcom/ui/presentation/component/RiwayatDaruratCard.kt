package com.example.slashcom.ui.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle as JavaTextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RiwayatDaruratCard(
    judul: String,
    tanggal: LocalDate,
    waktu: LocalDateTime,
    onClick: () -> Unit
) {
    val tanggalFormatted = tanggal.dayOfWeek.getDisplayName(JavaTextStyle.FULL, Locale("id"))
        .replaceFirstChar { it.uppercaseChar() } +
            ", ${tanggal.dayOfMonth} ${
                tanggal.month.getDisplayName(
                    JavaTextStyle.FULL,
                    Locale("id")
                )
            } ${tanggal.year}"
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val waktuFormatted = waktu.format(formatter)

    Box(
        modifier = Modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            )
            .fillMaxWidth()
            .height(76.dp)
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_darurat),
                contentDescription = "Darurat",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(30.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = judul,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = Color(0xFF121212)
                    )
                )
                Text(
                    text = tanggalFormatted,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFF7F7F7F)
                    )
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = waktuFormatted,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFF121212)
                    )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = false)
@Composable
fun RiwayatDaruratCardPreview() {
    RiwayatDaruratCard(
        judul = "Ibu",
        tanggal = LocalDate.of(2025, 7, 3),
        waktu = LocalDateTime.now(),
        onClick = {}
    )
}