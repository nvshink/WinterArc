package com.nvshink.domain.resouce

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T, val isLocal: Boolean = false, val onlineException: Exception? = null) : Resource<T>()
    data class Error<out T>(val exception: Throwable, val cachedData: T? = null) : Resource<T>()
}