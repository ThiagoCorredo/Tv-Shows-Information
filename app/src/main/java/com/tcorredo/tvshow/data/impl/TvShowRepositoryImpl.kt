package com.tcorredo.tvshow.data.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.domain.entity.TvShowDetails
import com.tcorredo.tvshow.data.domain.entity.TvShowEpisode
import com.tcorredo.tvshow.data.domain.entity.TvShowSeason
import com.tcorredo.tvshow.data.domain.repository.TvShowRepository
import com.tcorredo.tvshow.data.domain.source.TvShowPagingSource
import com.tcorredo.tvshow.data.remote.response.SearchTvShowResponse
import com.tcorredo.tvshow.data.remote.response.ShowResponse
import com.tcorredo.tvshow.data.remote.TvMazeService
import com.tcorredo.tvshow.data.remote.response.EpisodeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TvShowRepositoryImpl(
    private val tvMazeService: TvMazeService,
    private val dispatchers: CoroutineDispatchers,
    private val responseToDomain: Mapper<ShowResponse, TvShow>,
    private val searchResponseToDomain: Mapper<SearchTvShowResponse, TvShow>,
    private val episodeResponseToDomain: Mapper<EpisodeResponse, TvShowEpisode>
) : TvShowRepository {

    override fun getTvShows(query: String): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                TvShowPagingSource(
                    tvMazeService,
                    dispatchers,
                    responseToDomain,
                    searchResponseToDomain,
                    query
                )
            }
        )
            .flow
            .flowOn(dispatchers.io)
    }

    override fun getTvShowDetails(tvShow: TvShow?): Flow<TvShowDetails> = flow {
        val tvShowSeasons = tvMazeService.getTvShowSeasons(tvShowId = tvShow?.id)

        val mappedTvShowSeasons = tvShowSeasons.map { season ->
            val seasonEpisodes =
                tvMazeService.getSeasonEpisodes(season.id).map(episodeResponseToDomain)
            TvShowSeason(season.id, season.number, seasonEpisodes)
        }

        emit(TvShowDetails(tvShow, mappedTvShowSeasons))
    }.flowOn(dispatchers.io)

    companion object {
        private const val PAGE_SIZE = 20
    }
}
