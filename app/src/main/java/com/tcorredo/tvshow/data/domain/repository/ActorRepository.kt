package com.tcorredo.tvshow.data.domain.repository

import androidx.paging.PagingData
import com.tcorredo.tvshow.data.domain.entity.Actor
import kotlinx.coroutines.flow.Flow

interface ActorRepository {
    fun getActors(): Flow<PagingData<Actor>>
}