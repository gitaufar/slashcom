package com.example.slashcom.ui.presentation.user.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.slashcom.R
import com.example.slashcom.cache.ListQuestion
import com.example.slashcom.ui.presentation.component.BlueButtonFull
import com.example.slashcom.ui.presentation.component.QuestionCard
import com.example.slashcom.ui.presentation.component.RecorderText
import com.example.slashcom.ui.presentation.component.VoiceRecorder

enum class RecorderState {
    Idle, Recording, Finished
}

@Composable
fun RecorderScreen(viewModel: RecorderViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    var recorderState by remember { mutableStateOf(RecorderState.Idle) }
    val questions = remember { ListQuestion.getRandomQuestions(5) }

    var isPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(Unit) {
        if (!isPermissionGranted) {
            ActivityCompat.requestPermissions(
                (context as android.app.Activity),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                0
            )
        }
    }

    LaunchedEffect(Unit) {
        isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFFFFF8E1),
                        0.2f to Color(0xFFFFF8E1),
                        1.0f to Color(0xFFB3E5FC)
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .border(width = 0.3.dp, color = Color(0xFF7F7F7F))
                .height(90.dp)
                .padding(start = 20.dp, top = 40.dp, end = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(1.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable { navController.navigate("userDashboard") },
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "arrow_back",
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Check-In Suara",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(
                modifier = Modifier
                    .padding(1.dp)
                    .width(30.dp)
                    .height(30.dp),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (recorderState == RecorderState.Idle) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Rekam suara Ibu dengan menjawab pertanyaan di layar. Ceritakan secara jujur dan nyaman—kami akan membantu mengenali emosi Ibu saat ini.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF7F7F7F),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            QuestionCard(questions, state = recorderState)

            VoiceRecorder(
                onPlay = recorderState,
                onStart = {
                    recorderState = RecorderState.Recording
                    viewModel.startRecording(context)
                },
                onStop = {
                    recorderState = RecorderState.Finished
                    viewModel.stopRecording(context)
                }
            )
            when (recorderState) {
                RecorderState.Idle -> RecorderText(text = "Tekan untuk mulai merekam")
                RecorderState.Recording -> RecorderText(text = "Tekan untuk Berhenti")
                RecorderState.Finished -> BlueButtonFull(
                    text = "Lihat Hasil",
                    onClick = {
                        viewModel.uploadAudio() { success ->
                            if (success) {
                                navController.navigate("hasil") {
                                    popUpTo("recorder") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Upload gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }
    }
}
