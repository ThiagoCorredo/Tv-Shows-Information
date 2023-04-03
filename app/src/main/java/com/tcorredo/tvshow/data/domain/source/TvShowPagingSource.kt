package com.tcorredo.tvshow.data.domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.remote.response.SearchTvShowResponse
import com.tcorredo.tvshow.data.remote.response.ShowResponse
import com.tcorredo.tvshow.data.remote.TvMazeService
import kotlinx.coroutines.withContext

class TvShowPagingSource(
    private val tvMazeService: TvMazeService,
    private val dispatchers: CoroutineDispatchers,
    private val responseToDomain: Mapper<ShowResponse, TvShow>,
    private val searchResponseToDomain: Mapper<SearchTvShowResponse, TvShow>,
    private val query: String
) : PagingSource<Int, TvShow>() {

    private suspend fun getAllTvShowsFromRemote(page: Int): List<TvShow> {
        return withContext(dispatchers.io) {
            tvMazeService.getTvShows(page = page).map(responseToDomain)
        }
    }

    private suspend fun searchTvShowsFromRemote(): List<TvShow> {
        return withContext(dispatchers.io) {
            tvMazeService.searchTvShows(query = query).map(searchResponseToDomain)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val pageNumber = params.key ?: 0

        return try {
            if (query.isEmpty()) {
                val shows = getAllTvShowsFromRemote(pageNumber)
                LoadResult.Page(
                    data = shows,
                    prevKey = if (pageNumber == 0) null else pageNumber - 1,
                    nextKey = if (shows.isEmpty()) null else pageNumber + 1
                )
            } else {
                val shows = searchTvShowsFromRemote()
                LoadResult.Page(
                    data = shows,
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}