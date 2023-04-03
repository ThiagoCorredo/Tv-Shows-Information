package com.tcorredo.tvshow.data.mapper

import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.entity.TvShowEpisode
import com.tcorredo.tvshow.data.remote.response.EpisodeResponse

class SeasonEpisodeResponseToSeasonEpisodeDomainMapper : Mapper<EpisodeResponse, TvShowEpisode> {
    override fun invoke(episodeResponse: EpisodeResponse): TvShowEpisode {
        return TvShowEpisode(
            id = episodeResponse.id,
            episodeImage = episodeResponse.images?.medium ?: "",
            episodeName = episodeResponse.name
        )
    }
}
