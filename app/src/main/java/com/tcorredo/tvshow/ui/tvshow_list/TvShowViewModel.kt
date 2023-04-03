package com.tcorredo.tvshow.ui.tvshow_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.domain.usecase.GetTvShowUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TvShowViewModel(
    private val getTvShowUseCase: GetTvShowUseCase
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<TvShow>>
    val accept: (UiAction) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = DEFAULT_QUERY)) }

        pagingDataFlow = searches
            .flatMapLatest { getAllTvShows(query = it.query) }
            .cachedIn(viewModelScope)

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    private fun getAllTvShows(query: String): Flow<PagingData<TvShow>> =
        getTvShowUseCase.invoke(query)

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}

sealed class UiAction {
    data class Search(val query: String) : UiAction()
}
