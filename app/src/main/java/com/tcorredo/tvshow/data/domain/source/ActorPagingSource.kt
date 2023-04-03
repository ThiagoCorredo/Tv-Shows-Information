package com.tcorredo.tvshow.data.domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.data.remote.TvMazeService
import com.tcorredo.tvshow.data.remote.response.ActorResponse
import kotlinx.coroutines.withContext

class ActorPagingSource(
    private val tvMazeService: TvMazeService,
    private val dispatchers: CoroutineDispatchers,
    private val responseToDomain: Mapper<ActorResponse, Actor>
) : PagingSource<Int, Actor>() {

    private suspend fun getAllActorsFromRemote(page: Int): List<Actor> {
        return withContext(dispatchers.io) {
            tvMazeService.getActors(page = page).map(responseToDomain)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {
        val pageNumber = params.key ?: 0

        return try {
            val shows = getAllActorsFromRemote(pageNumber)
            LoadResult.Page(
                data = shows,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (shows.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, Actor>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
