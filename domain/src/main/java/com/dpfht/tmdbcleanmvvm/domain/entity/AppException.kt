package com.dpfht.tmdbcleanmvvm.domain.entity

class AppException(
    override val message: String = ""
): Exception(message)
