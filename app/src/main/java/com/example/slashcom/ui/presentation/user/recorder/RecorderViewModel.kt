package com.example.slashcom.ui.presentation.user.recorder

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slashcom.data.model.MoodResponse
import com.example.slashcom.domain.usecase.MoodUseCase
import com.example.slashcom.utils.AudioWavRecorder
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.slashcom.data.api.UploadAudioRepositoryImpl
import com.example.slashcom.data.repository.DashboardRepositoryImpl
import com.example.slashcom.domain.model.Mood
import com.example.slashcom.domain.usecase.UploadAudioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val moodUseCase: MoodUseCase,
) : ViewModel() {

    private val repository: DashboardRepositoryImpl = DashboardRepositoryImpl()
    private lateinit var recorder: AudioWavRecorder
    private lateinit var outputFile: File
    private val uploadAudioUseCase = UploadAudioUseCase(UploadAudioRepositoryImpl())

    private val _isKrisis = mutableStateOf<Boolean?>(null)
    val isKrisis: State<Boolean?> = _isKrisis

    private val _moodResponse = MutableStateFlow<MoodResponse?>(null)
    val moodResponse: StateFlow<MoodResponse?> = _moodResponse

    fun analyzeMood(outputFile: File, context: Context) {
        viewModelScope.launch {
            val result = moodUseCase(outputFile)
            _moodResponse.value = result

            // Tampilkan hasil ke Toast
            if (result.error != null) {
                Toast.makeText(context, "❌ Error: ${result.error}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    context,
                    """
                🙂 Emosi: ${result.predicted_emotion}
                📉 Stres: ${"%.2f".format(result.predicted_stress_level)}
                """.trimIndent(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    fun startRecording(context: Context) {
        val directory = File(
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),
            "SlashCom"
        )

        if (!directory.exists()) {
            directory.mkdirs()
        }

        outputFile = File(directory, "audio_record_${System.currentTimeMillis()}.mp4")
        recorder = AudioWavRecorder(outputFile)
        recorder.startRecording(context)
        Toast.makeText(
            context,
            "Recording started, saved at: ${outputFile.absolutePath}",
            Toast.LENGTH_SHORT
        ).show()
    }


    fun stopRecording(context: Context) {
        recorder.stopRecording()

        if (outputFile.exists() && outputFile.length() > 0) {
            Log.d("Recorder", "WAV File: ${outputFile.absolutePath}, size: ${outputFile.length()}")

            analyzeMood(outputFile, context)
        } else {
            Toast.makeText(context, "Recording failed or empty", Toast.LENGTH_LONG).show()
            Toast.makeText(
                context,
                "Recording saved at: ${outputFile.absolutePath}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    fun getRecordedFile(): File? {
        return if (::outputFile.isInitialized) outputFile else null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadAudio(onResult: (Boolean) -> Unit) {
        val file = getRecordedFile()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val formattedDate = LocalDateTime.now().format(formatter)
        if (file != null && file.exists()) {
            viewModelScope.launch {
                try {
//                    val response = uploadAudioUseCase.invoke(file)
//                    _isKrisis.value = response?.is_krisis
                    file.delete()
                    repository.addMood(Mood(emosi = moodResponse.value!!.predicted_emotion, stress = moodResponse.value!!.predicted_stress_level.toInt(), crisis = false, date = formattedDate))
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