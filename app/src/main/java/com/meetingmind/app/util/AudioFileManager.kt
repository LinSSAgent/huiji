package com.meetingmind.app.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.RandomAccessFile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioFileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val audioDir: File
        get() {
            val dir = File(context.filesDir, "recordings")
            if (!dir.exists()) dir.mkdirs()
            return dir
        }

    private var outputStream: FileOutputStream? = null
    private var currentFile: File? = null
    private var totalDataSize: Int = 0

    fun createNewRecording(meetingId: Long): File {
        val fileName = "meeting_${meetingId}_${System.currentTimeMillis()}.wav"
        currentFile = File(audioDir, fileName)
        outputStream = FileOutputStream(currentFile!!)
        totalDataSize = 0
        
        // Write WAV header placeholder (44 bytes)
        writeWavHeader(outputStream!!, 0)
        
        return currentFile!!
    }

    fun writeAudioData(data: ByteArray, size: Int) {
        outputStream?.write(data, 0, size)
        totalDataSize += size
    }

    fun finishRecording(): String? {
        outputStream?.close()
        outputStream = null
        
        // Update WAV header with actual data size
        currentFile?.let { file ->
            updateWavHeader(file, totalDataSize)
            return file.absolutePath
        }
        return null
    }

    fun cancelRecording() {
        outputStream?.close()
        outputStream = null
        currentFile?.delete()
        currentFile = null
    }

    fun getRecordingFile(path: String): File? {
        val file = File(path)
        return if (file.exists()) file else null
    }

    fun deleteRecording(path: String) {
        File(path).delete()
    }

    private fun writeWavHeader(out: FileOutputStream, dataSize: Int) {
        val sampleRate = 16000
        val channels = 1
        val bitsPerSample = 16
        val byteRate = sampleRate * channels * bitsPerSample / 8
        val blockAlign = channels * bitsPerSample / 8

        // RIFF header
        out.write("RIFF".toByteArray())
        out.write(intToByteArray(36 + dataSize)) // File size - 8
        out.write("WAVE".toByteArray())

        // fmt sub-chunk
        out.write("fmt ".toByteArray())
        out.write(intToByteArray(16)) // Sub-chunk size
        out.write(shortToByteArray(1)) // PCM format
        out.write(shortToByteArray(channels.toShort()))
        out.write(intToByteArray(sampleRate))
        out.write(intToByteArray(byteRate))
        out.write(shortToByteArray(blockAlign.toShort()))
        out.write(shortToByteArray(bitsPerSample.toShort()))

        // data sub-chunk
        out.write("data".toByteArray())
        out.write(intToByteArray(dataSize))
    }

    private fun updateWavHeader(file: File, dataSize: Int) {
        val raf = RandomAccessFile(file, "rw")
        // Update file size at offset 4
        raf.seek(4)
        raf.write(intToByteArray(36 + dataSize))
        // Update data size at offset 40
        raf.seek(40)
        raf.write(intToByteArray(dataSize))
        raf.close()
    }

    private fun intToByteArray(value: Int): ByteArray {
        return byteArrayOf(
            (value and 0xFF).toByte(),
            ((value shr 8) and 0xFF).toByte(),
            ((value shr 16) and 0xFF).toByte(),
            ((value shr 24) and 0xFF).toByte()
        )
    }

    private fun shortToByteArray(value: Short): ByteArray {
        return byteArrayOf(
            (value.toInt() and 0xFF).toByte(),
            ((value.toInt() shr 8) and 0xFF).toByte()
        )
    }
}
