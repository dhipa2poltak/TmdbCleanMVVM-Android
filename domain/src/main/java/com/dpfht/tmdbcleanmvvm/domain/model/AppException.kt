package com.dpfht.tmdbcleanmvvm.domain.model

class AppException(
    override val message: String = ""
): Exception(message)
