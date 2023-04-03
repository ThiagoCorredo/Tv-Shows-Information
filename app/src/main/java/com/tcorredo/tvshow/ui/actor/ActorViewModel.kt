package com.tcorredo.tvshow.ui.actor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.data.domain.usecase.GetActorUseCase
import kotlinx.coroutines.flow.Flow

class ActorViewModel(
    private val getActorUseCase: GetActorUseCase
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Actor>>

    init {
        pagingDataFlow = getAllActors().cachedIn(viewModelScope)
    }

    fun getAllActors(): Flow<PagingData<Actor>> =
        getActorUseCase.invoke()
}