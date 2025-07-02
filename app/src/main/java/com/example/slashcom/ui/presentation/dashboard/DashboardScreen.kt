package com.example.slashcom.ui.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R
import com.example.slashcom.cache.UserData

@Preview
@Composable
fun DashboardScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD1E8FF))
            .padding(top = 40.dp)
    ){
        Text(
            text = "Halo, Ibu ${UserData.userName}",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontWeight = FontWeight(600),
                color = Color.Black,
            )
        )
        Text(
            text = "Bagaimana kabar ibu hari ini?",
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight(400),
                color = Color.Black,
            )
        )
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 30.dp))
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
            ) {

            }
        }
    }
}