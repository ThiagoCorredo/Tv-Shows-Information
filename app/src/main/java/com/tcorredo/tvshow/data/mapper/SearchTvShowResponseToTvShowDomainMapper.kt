package com.tcorredo.tvshow.data.mapper

import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.remote.response.SearchTvShowResponse

class SearchTvShowResponseToTvShowDomainMapper : Mapper<SearchTvShowResponse, TvShow> {
    override fun invoke(showResponse: SearchTvShowResponse): TvShow {
        return TvShow(
            id = showResponse.tvShow.id,
            name = showResponse.tvShow.name,
            imageMedium = showResponse.tvShow.images?.medium ?: "",
            imageOriginal = showResponse.tvShow.images?.original ?: "",
            scheduleDays = showResponse.tvShow.schedule.days,
            scheduleTime = showResponse.tvShow.schedule.time,
            genres = showResponse.tvShow.genres,
            summary = showResponse.tvShow.name
        )
    }
}
