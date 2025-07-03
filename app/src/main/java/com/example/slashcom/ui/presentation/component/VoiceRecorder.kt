package com.example.slashcom.ui.presentation.componen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slashcom.R
import com.example.slashcom.ui.presentation.recorder.RecorderState
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun VoiceRecorder(
    onPlay: RecorderState,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    var timeInSeconds by remember { mutableIntStateOf(0) }
    val backgroundBrush: Brush = if (onPlay == RecorderState.Finished) {
        SolidColor(Color(0xFFCACACA))
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFF472B6),
                Color(0xFFC084FC)
            )
        )
    }
    LaunchedEffect(onPlay) {
        while (onPlay == RecorderState.Recording) {
            delay(1000)
            timeInSeconds++
        }
    }

    val formattedTime = String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = CircleShape)
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            Text(
                text = formattedTime,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFFF472B6), Color(0xFF8B5CF6))
                    )
                )
            )
        }

        Spacer(modifier = Modifier.size(24.dp))

        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(backgroundBrush)
                .clickable(enabled = onPlay != RecorderState.Finished) {
                    when (onPlay) {
                        RecorderState.Idle -> onStart()
                        RecorderState.Recording -> onStop()
                        RecorderState.Finished -> {} // Do nothing
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            val icon = when (onPlay) {
                RecorderState.Recording -> R.drawable.stop_icon
                else -> R.drawable.mic_logo
            }

            Image(
                painter = painterResource(id = icon),
                contentDescription = if (onPlay == RecorderState.Recording) "Stop" else "Mic"
            )
        }
    }
}
