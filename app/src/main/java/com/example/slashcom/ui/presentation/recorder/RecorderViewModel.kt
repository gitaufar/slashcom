package com.example.slashcom.ui.presentation.recorder

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import android.media.MediaPlayer

class RecorderViewModel : ViewModel() {

    private val _amplitude = mutableStateOf(0)
    val amplitude: State<Int> get() = _amplitude

    private var amplitudeJob: Job? = null

    fun startVolumeUpdates() {
        amplitudeJob = viewModelScope.launch {
            while (true) {
                delay(100)
                if (::recorder.isInitialized) {
                    _amplitude.value = recorder.getAmplitude()
                }
            }
        }
    }

    fun stopVolumeUpdates() {
        amplitudeJob?.cancel()
        _amplitude.value = 0
    }


    private lateinit var recorder: AudioRecorder
    private lateinit var outputFile: File

    fun startRecording(context: Context) {
        val directory = File(
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),
            "SlashCom"
        )

        if (!directory.exists()) {
            directory.mkdirs()
        }

        outputFile = File(directory, "audio_record_${System.currentTimeMillis()}.mp4")
        recorder = AudioRecorder(context, outputFile)
        recorder.startRecording()
        Toast.makeText(
            context,
            "Recording started, saved at: ${outputFile.absolutePath}",
            Toast.LENGTH_SHORT
        ).show()
        startVolumeUpdates()
    }


    fun stopRecording(context: Context) {
        recorder.stopRecording()
        Toast.makeText(context, "Recording saved at: ${outputFile.absolutePath}", Toast.LENGTH_LONG)
            .show()
        stopVolumeUpdates()
    }

    private var mediaPlayer: MediaPlayer? = null

    fun playRecording(context: Context) {
        if (::outputFile.isInitialized) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(outputFile.absolutePath)
                prepare()
                start()
            }

            Toast.makeText(context, "Memutar rekaman...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Tidak ada file rekaman", Toast.LENGTH_SHORT).show()
        }
    }


    fun getSavedAudioFile(): File? {
        return if (::outputFile.isInitialized) outputFile else null
    }
}