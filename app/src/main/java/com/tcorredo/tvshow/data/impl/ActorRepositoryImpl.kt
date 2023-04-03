package com.tcorredo.tvshow.data.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.data.domain.repository.ActorRepository
import com.tcorredo.tvshow.data.domain.source.ActorPagingSource
import com.tcorredo.tvshow.data.remote.TvMazeService
import com.tcorredo.tvshow.data.remote.response.ActorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ActorRepositoryImpl(
    private val tvMazeService: TvMazeService,
    private val dispatchers: CoroutineDispatchers,
    private val responseToDomain: Mapper<ActorResponse, Actor>,
) : ActorRepository {

    override fun getActors(): Flow<PagingData<Actor>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                ActorPagingSource(
                    tvMazeService,
                    dispatchers,
                    responseToDomain
                )
            }
        )
            .flow
            .flowOn(dispatchers.io)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}