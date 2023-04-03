package com.tcorredo.tvshow.ui.tvshow_details

import androidx.lifecycle.ViewModel
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.domain.usecase.GetTvShowDetailsUseCase
import kotlinx.coroutines.flow.*

class TvShowDetailsViewModel(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase
) : ViewModel() {

    fun getTvShowDetails(tvShow: TvShow?): Flow<TvShowDetailsState> = flow {
        getTvShowDetailsUseCase.invoke(tvShow = tvShow)
            .onStart { emit(TvShowDetailsState.Loading) }
            .catch { emit(TvShowDetailsState.Error(it.message)) }
            .collect { emit(TvShowDetailsState.Success(it)) }
    }
}
