package com.example.slashcom.ui.presentation.user.recorder

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
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
import com.example.slashcom.domain.usecase.UploadAudioUseCase
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val moodUseCase: MoodUseCase,
) : ViewModel() {

    private lateinit var recorder: AudioWavRecorder
    private lateinit var outputFile: File
    private val uploadAudioUseCase = UploadAudioUseCase(UploadAudioRepositoryImpl())

    private val _isKrisis = mutableStateOf<Boolean?>(null)
    val isKrisis: State<Boolean?> = _isKrisis

    private val _moodResponse = MutableLiveData<MoodResponse>()
    val moodResponse: LiveData<MoodResponse> = _moodResponse

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

    fun uploadAudio(onResult: (Boolean) -> Unit) {
        val file = getRecordedFile()
        if (file != null && file.exists()) {
            Log.d("masuk1","masuk kang")
            viewModelScope.launch {
                Log.d("masuk2","masuk juga kang")
                try {
                    Log.d("masuk3","yes masuk kang")
                    val response = uploadAudioUseCase.invoke(file)
                    _isKrisis.value = response?.is_krisis
                    Log.d("masuk6","${_isKrisis.value}")
                    onResult(true)
                } catch (e: Exception) {
                    Log.d("masuk4","masuk error kang")
                    _isKrisis.value = null
                    onResult(false)
                }
            }
        } else {
            Log.d("masuk5","masuk gaada file kang")
            _isKrisis.value = null
            onResult(false)
        }
    }
}