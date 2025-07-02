package com.example.slashcom.ui.presentation.recorder

import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

class AudioRecorder(private val context: Context, private val outputFile: File) {
    private var recorder: MediaRecorder? = null

    fun startRecording() {
        try {
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputFile.absolutePath)
                prepare()
                start()
            }

            Log.d("AudioRecorder", "Recording started at: ${outputFile.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal mulai merekam", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        Log.d("AudioRecorder", "Recording saved at: ${outputFile.absolutePath}")
    }

    fun getAmplitude(): Int {
        return try {
            recorder?.maxAmplitude ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun getOutputFilePath(): String? {
        return outputFile.absolutePath
    }
}
