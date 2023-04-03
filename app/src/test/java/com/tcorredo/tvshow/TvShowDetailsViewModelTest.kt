package com.tcorredo.tvshow

import com.tcorredo.tvshow.data.domain.entity.TvShowDetails
import com.tcorredo.tvshow.data.domain.usecase.GetTvShowDetailsUseCase
import com.tcorredo.tvshow.ui.tvshow_details.TvShowDetailsState
import com.tcorredo.tvshow.ui.tvshow_details.TvShowDetailsViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class TvShowDetailsViewModelTest : KoinTest {

    private val tvShowDetails = mockk<TvShowDetails>()
    private val getTvShowDetailsUseCase = mockk<GetTvShowDetailsUseCase>()
    private lateinit var viewModel: TvShowDetailsViewModel

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { viewModel }
                    single { getTvShowDetailsUseCase }
                }
            )
        }
        viewModel = TvShowDetailsViewModel(getTvShowDetailsUseCase)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getTvShowDetails should emit Loading and Success states`() {
        // Given
        val tvShowDetailsStateLoading = TvShowDetailsState.Loading
        val tvShowDetailsStateSuccess = TvShowDetailsState.Success(tvShowDetails)
        coEvery { getTvShowDetailsUseCase.invoke(tvShowDetails.tvShow) } returns flowOf(
            tvShowDetails
        )

        // When
        val result: List<TvShowDetailsState> = mockk()
        coEvery { viewModel.getTvShowDetails(tvShowDetails.tvShow).toList() } returns result

        // Define mock answer for result[0]
        every { result[0] } returns tvShowDetailsStateLoading

        // Define mock answer for result[1]
        every { result[1] } returns tvShowDetailsStateSuccess

        // Then
        assert(result[0] == tvShowDetailsStateLoading)
        assert(result[1] == tvShowDetailsStateSuccess)
    }

    @Test
    fun `getTvShowDetails should emit Loading and Error states`() {
        // Given
        val errorMessage = "Something went wrong"
        val tvShowDetailsStateLoading = TvShowDetailsState.Loading
        val tvShowDetailsStateError = TvShowDetailsState.Error(errorMessage)
        coEvery { getTvShowDetailsUseCase.invoke(tvShowDetails.tvShow) } throws RuntimeException(
            errorMessage
        )

        // When
        val result: List<TvShowDetailsState> = mockk()
        coEvery { viewModel.getTvShowDetails(tvShowDetails.tvShow).toList() } returns result


        // Define mock answer for result[0]
        every { result[0] } returns tvShowDetailsStateLoading

        // Define mock answer for result[1]
        every { result[1] } returns tvShowDetailsStateError

        // Then
        assert(result[0] == tvShowDetailsStateLoading)
        assert(result[1] == tvShowDetailsStateError)
    }
}