package com.example.slashcom.ui.presentation.recorder

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecorderScreen(viewModel: RecorderViewModel = viewModel(), modifier: Modifier) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        ActivityCompat.requestPermissions(
            (context as android.app.Activity),
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val amplitude by viewModel.amplitude
        val animatedHeight by animateDpAsState(targetValue = (amplitude / 50).coerceIn(10, 200).dp)

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight)
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
        )

        Button(onClick = { viewModel.startRecording(context) }, modifier = Modifier.fillMaxWidth()) {
            Text("Start Recording")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.stopRecording(context) }, modifier = Modifier.fillMaxWidth()) {
            Text("Stop Recording")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val file = viewModel.getSavedAudioFile()
            Toast.makeText(context,file?.absolutePath ?: "belum ada file", Toast.LENGTH_LONG).show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Upload Audio")
        }
    }
}