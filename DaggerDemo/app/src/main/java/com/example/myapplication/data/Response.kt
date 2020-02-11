package com.example.myapplication.data

import java.lang.Exception

sealed class Response<out T> {
    data class Success<out T>(val value: T): Response<T>()
    data class Error(val code: Int = 0, val error: String = "", val exception: Exception? = null): Response<Nothing>()
}