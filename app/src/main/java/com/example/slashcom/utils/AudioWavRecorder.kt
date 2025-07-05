package com.example.slashcom.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import java.io.*

class AudioWavRecorder(
    private val outputFile: File,
    private val sampleRate: Int = 16000,
    private val channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
    private val audioEncoding: Int = AudioFormat.ENCODING_PCM_16BIT,
) {
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private var isRecording = false
    private var bufferSize = 0
    
    fun startRecording(context: Context) {
        bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioEncoding)
        bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioEncoding)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioEncoding,
            bufferSize
        )
        audioRecord?.startRecording()
        isRecording = true
        
        recordingThread = Thread {
            writeAudioDataToFile()
        }.also { it.start() }
    }
    
    fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
        recordingThread = null
        
        // Update header
        updateWavHeader(outputFile, sampleRate, 1, 16)
    }
    
    private fun writeAudioDataToFile() {
        val outputStream = BufferedOutputStream(FileOutputStream(outputFile))
        val pcmData = ByteArrayOutputStream()
        
        val buffer = ByteArray(bufferSize)
        while (isRecording) {
            val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
            if (read > 0) {
                pcmData.write(buffer, 0, read)
            }
        }
        
        val audioBytes = pcmData.toByteArray()
        writeWavHeader(outputStream, audioBytes.size, sampleRate, 1, 16)
        outputStream.write(audioBytes)
        outputStream.close()
    }
    
    private fun writeWavHeader(
        out: OutputStream,
        pcmSize: Int,
        sampleRate: Int,
        channels: Int,
        bitsPerSample: Int,
    ) {
        val byteRate = sampleRate * channels * bitsPerSample / 8
        val totalDataLen = pcmSize + 36
        
        val header = ByteArray(44)
        header[0] = 'R'.code.toByte(); header[1] = 'I'.code.toByte(); header[2] =
            'F'.code.toByte(); header[3] = 'F'.code.toByte()
        writeInt(header, 4, totalDataLen)
        header[8] = 'W'.code.toByte(); header[9] = 'A'.code.toByte(); header[10] =
            'V'.code.toByte(); header[11] = 'E'.code.toByte()
        header[12] = 'f'.code.toByte(); header[13] = 'm'.code.toByte(); header[14] =
            't'.code.toByte(); header[15] = ' '.code.toByte()
        writeInt(header, 16, 16)
        writeShort(header, 20, 1)
        writeShort(header, 22, channels.toShort())
        writeInt(header, 24, sampleRate)
        writeInt(header, 28, byteRate)
        writeShort(header, 32, (channels * bitsPerSample / 8).toShort())
        writeShort(header, 34, bitsPerSample.toShort())
        header[36] = 'd'.code.toByte(); header[37] = 'a'.code.toByte(); header[38] =
            't'.code.toByte(); header[39] = 'a'.code.toByte()
        writeInt(header, 40, pcmSize)
        
        out.write(header)
    }
    
    private fun updateWavHeader(file: File, sampleRate: Int, channels: Int, bitsPerSample: Int) {
        val totalAudioLen = file.length() - 44
        val byteRate = sampleRate * channels * bitsPerSample / 8
        val totalDataLen = totalAudioLen + 36
        
        val randomAccessFile = RandomAccessFile(file, "rw")
        randomAccessFile.seek(4)
        randomAccessFile.writeInt(Integer.reverseBytes(totalDataLen.toInt()))
        randomAccessFile.seek(40)
        randomAccessFile.writeInt(Integer.reverseBytes(totalAudioLen.toInt()))
        randomAccessFile.close()
    }
    
    private fun writeInt(data: ByteArray, offset: Int, value: Int) {
        data[offset] = (value and 0xff).toByte()
        data[offset + 1] = ((value shr 8) and 0xff).toByte()
        data[offset + 2] = ((value shr 16) and 0xff).toByte()
        data[offset + 3] = ((value shr 24) and 0xff).toByte()
    }
    
    private fun writeShort(data: ByteArray, offset: Int, value: Short) {
        data[offset] = (value.toInt() and 0xff).toByte()
        data[offset + 1] = ((value.toInt() shr 8) and 0xff).toByte()
    }
}
