package io.github.hunachi.shared.network

sealed class Result<out R, out E>{

    class Success<out T, out E>(val data: T): Result<T, E>()

    class Error<out T, out E>(val e: E): Result<T, E>()
}