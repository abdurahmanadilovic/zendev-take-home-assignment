package com.example.postsviewer.domain

suspend fun <T> getResult(action: suspend () -> T): Result<T> {
    return try {
        Result.success(action.invoke())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
