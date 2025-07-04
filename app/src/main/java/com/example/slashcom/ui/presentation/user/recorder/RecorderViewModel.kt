package com.example.slashcom.ui.presentation.user.recorder

import android.content.Context
import android.media.MediaPlayer
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.data.api.UploadAudioRepositoryImpl
import com.example.slashcom.domain.usecase.UploadAudioUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class RecorderViewModel : ViewModel() {

    private val _amplitude = mutableStateOf(0)
    val amplitude: State<Int> = _amplitude

    private var amplitudeJob: Job? = null

    private lateinit var recorder: AudioRecorder
    private lateinit var outputFile: File

    private val _isKrisis = mutableStateOf<Boolean?>(null)
    val isKrisis: State<Boolean?> = _isKrisis


    // Inisialisasi UseCase secara manual (tanpa DI)
    private val uploadAudioUseCase = UploadAudioUseCase(UploadAudioRepositoryImpl())

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
        Toast.makeText(
            context,
            "Recording saved at: ${outputFile.absolutePath}",
            Toast.LENGTH_LONG
        ).show()

        stopVolumeUpdates()
    }

    private fun startVolumeUpdates() {
        amplitudeJob = viewModelScope.launch {
            while (true) {
                delay(100)
                if (::recorder.isInitialized) {
                    _amplitude.value = recorder.getAmplitude()
                }
            }
        }
    }

    private fun stopVolumeUpdates() {
        amplitudeJob?.cancel()
        _amplitude.value = 0
    }

    fun getRecordedFile(): File? {
        return if (::outputFile.isInitialized) outputFile else null
    }

    fun uploadAudio(onResult: (Boolean) -> Unit) {
        val file = getRecordedFile()
        if (file != null && file.exists()) {
            viewModelScope.launch {
                try {
                    val response = uploadAudioUseCase.invoke(file)
                    _isKrisis.value = response?.is_krisis
                    onResult(true)
                } catch (e: Exception) {
                    _isKrisis.value = null
                    onResult(false)
                }
            }
        } else {
            _isKrisis.value = null
            onResult(false)
        }
    }
}
