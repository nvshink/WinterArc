package com.nvshink.domain.localstorage

interface LocalStorage {
    suspend fun saveImageToLocalStorage(uriString: String, fileName: String): String?

    suspend fun deleteImageFromLocalStorage(uriString: String): Boolean
}