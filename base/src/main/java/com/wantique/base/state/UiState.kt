package com.wantique.base.state

sealed class UiState<out T> {
    object Initialize : UiState<Nothing>()
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error(val error: Throwable?): UiState<Nothing>()

    /*
    fun<T> isSuccess(value: UiState<T>): T? {
        return if(value is Success) {
            value.data
        } else {
            null
        }
    }

     */
}

fun <T> UiState<T>.isSuccessOrNull(): T? {
    return if(this is UiState.Success) {
        data
    } else {
        null
    }
}

fun <T> UiState<T>.isErrorOrNull(): Throwable? {
    return if(this is UiState.Error) {
        error
    } else {
        null
    }
}
