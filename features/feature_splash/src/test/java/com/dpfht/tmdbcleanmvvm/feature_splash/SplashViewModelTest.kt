package com.dpfht.tmdbcleanmvvm.feature_splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashViewModel

    @Mock
    private lateinit var hasFinishedDelayingObserver: Observer<Boolean>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashViewModel()
    }

    @Test
    fun `ensuring has finished delaying when start() method in SplashViewModel is called`() = runTest {
        viewModel.hasFinishedDelaying.observeForever(hasFinishedDelayingObserver)
        viewModel.start()

        advanceUntilIdle()

        verify(hasFinishedDelayingObserver).onChanged(eq(true))
    }
}
