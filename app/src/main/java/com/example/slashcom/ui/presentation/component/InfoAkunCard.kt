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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.slashcom.R

@Composable
fun InfoAkunCard(
    username: String,
    email: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000),
                shape = RoundedCornerShape(15.dp)
            )
            .width(372.dp)
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Informasi Pribadi",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color(0xFF121212)
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit Icon",
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .clickable { onEditClick() }
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoItem(label = "Username", value = "@$username")
            InfoItem(label = "Email", value = email)
        }
    }
}

@Composable
private fun InfoItem(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label     :",
            modifier = Modifier.width(110.dp),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = Color(0xFF7F7F7F)
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = Color(0xFF7F7F7F)
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewInfoAkunCard() {
    InfoAkunCard(
        username = "safirarhm",
        email = "safirarhm@gmail.com",
        onEditClick = {}
    )
}