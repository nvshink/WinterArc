package com.nvshink.winterarc.data.utils

import android.content.Context
import android.net.Uri
import java.io.File

 internal fun saveImageToLocalStorage(context: Context, uri: Uri): Uri? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val fileName = "saved_image_${System.currentTimeMillis()}.jpg"
    val outputFile = File(context.filesDir, fileName)

    inputStream?.use { input ->
        outputFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return Uri.fromFile(outputFile)
}

internal fun deleteImageFromLocalStorage(uri: Uri): Boolean {
    val file = File(uri.path ?: return false)
    return file.delete()
}