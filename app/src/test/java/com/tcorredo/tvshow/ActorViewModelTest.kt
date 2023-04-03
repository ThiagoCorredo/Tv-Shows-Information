package com.tcorredo.tvshow

import androidx.paging.PagingData
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.data.domain.usecase.GetActorUseCase
import com.tcorredo.tvshow.ui.actor.ActorViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class ActorViewModelTest : KoinTest {

    @get:Rule
    val instantExecutionRule = InstantExecutionRule()

    private lateinit var viewModel: ActorViewModel
    private val getActorUseCase: GetActorUseCase = mockk()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { viewModel }
                    single { getActorUseCase }
                }
            )
        }
        viewModel = ActorViewModel(getActorUseCase)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getAllActors should return paging data`() = runBlockingTest {
        // Given
        val actors: List<Actor> = mockk()
        val pagingData = PagingData.from(actors)
        coEvery { getActorUseCase.invoke() } returns flowOf(pagingData)

        // When
        val result = viewModel.getAllActors().first()

        // Then
        assertEquals(pagingData, result)
        coVerify(exactly = 1) {
            getActorUseCase.invoke()
        }
    }
}
