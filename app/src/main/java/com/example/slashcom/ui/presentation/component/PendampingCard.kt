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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R
import com.example.slashcom.cache.UserData

@Composable
fun PendampingCard(
    name: String,
    onDeleteClick: () -> Unit,
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
            .height(103.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(15.dp))
            .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if(UserData.isIbu) "Pendamping" else "Ibu",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                color = Color(0xFF121212)
            )
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pendamping),
                    contentDescription = "Pendamping Icon",
                    modifier = Modifier
                        .padding(1.dp)
                        .width(35.dp)
                        .height(35.dp)
                )
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color(0xFF121212)
                    )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_hapus),
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .clickable { onDeleteClick() }
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewPendampingCard() {
    PendampingCard(
        name = "Adi Wijaya",
        onDeleteClick = { }
    )
}
