package com.dpfht.tmdbcleanmvvm.domain.usecase

import com.dpfht.tmdbcleanmvvm.domain.repository.AppRepository
import org.mockito.Mock

open class BaseUseCaseTest {

  @Mock
  protected lateinit var appRepository: AppRepository
}
