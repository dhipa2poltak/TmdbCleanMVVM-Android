package com.dpfht.tmdbcleanmvvm.core.usecase

sealed class UseCaseResultWrapper<out T> {
  data class Success<out T>(val value: T): UseCaseResultWrapper<T>()
  data class ErrorResult(val message: String): UseCaseResultWrapper<Nothing>()
}
