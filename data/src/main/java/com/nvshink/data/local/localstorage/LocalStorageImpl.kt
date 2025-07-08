package com.nvshink.data.local.localstorage

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.nvshink.domain.localstorage.LocalStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import java.io.File

class LocalStorageImpl @Inject constructor (
    @ApplicationContext private val context: Context
) : LocalStorage {

    override suspend fun saveImageToLocalStorage(uriString: String, fileName: String): String? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uriString.toUri())
        val outputFile = File(context.filesDir, fileName)

        inputStream?.use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        val uri: Uri? = Uri.fromFile(outputFile)
        return uri?.toString()
    }

    override suspend fun deleteImageFromLocalStorage(uriString: String): Boolean {
        val file = File(uriString.toUri().path ?: return false)
        return file.delete()
    }
}