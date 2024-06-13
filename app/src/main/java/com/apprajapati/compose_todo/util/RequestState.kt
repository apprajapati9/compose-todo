package com.apprajapati.compose_todo.util

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()

    data class Success<T>(val data: T) : RequestState<T>()

    data class Error<T>(val error: Throwable) : RequestState<T>()
}