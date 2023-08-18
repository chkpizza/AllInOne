package com.wantique.base.state

sealed class Resource<out T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val error: Throwable?): Resource<Nothing>()
}