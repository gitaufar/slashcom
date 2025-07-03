package com.example.slashcom.ui.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.user.recorder.RecorderState

@Composable
fun QuestionCard(
    listQuestion: List<String>,
    state: RecorderState = RecorderState.Idle
) {
    val isIdle = state == RecorderState.Idle
    val animatedSpacing by animateDpAsState(
        targetValue = if (isIdle) 5.dp else 10.dp,
        label = "ItemSpacing"
    )
    val animatedPadding by animateDpAsState(
        targetValue = if (isIdle) 0.dp else 5.dp,
        label = "ItemPadding"
    )
    Column(
        modifier = Modifier
            .shadow(
                elevation = if (isIdle) 4.dp else 0.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .fillMaxWidth()
            .background(
                color = if (isIdle) Color.White else Color.Transparent,
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(animatedSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isIdle) {
            Text(
                text = "💡 Mari kira mulai identifikasi!",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2D82D5),
                    textAlign = TextAlign.Center,
                )
            )
        }
        listQuestion.forEachIndexed { index, question ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (!isIdle) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(10)
                    )
                    .padding(animatedPadding)
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isIdle) "${index + 1}. $question" else question,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF7F7F7F),
                        textAlign = if (isIdle) TextAlign.Start else TextAlign.Center
                    )
                )
            }
        }
    }
}
