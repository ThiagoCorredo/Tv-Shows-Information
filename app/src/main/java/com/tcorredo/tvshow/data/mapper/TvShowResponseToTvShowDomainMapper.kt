package com.tcorredo.tvshow.data.mapper

import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.remote.response.ShowResponse

class TvShowResponseToTvShowDomainMapper : Mapper<ShowResponse, TvShow> {
    override fun invoke(showResponse: ShowResponse): TvShow {
        return TvShow(
            id = showResponse.id,
            name = showResponse.name,
            imageMedium = showResponse.images?.medium ?: "",
            imageOriginal = showResponse.images?.original ?: "",
            scheduleDays = showResponse.schedule.days,
            scheduleTime = showResponse.schedule.time,
            genres = showResponse.genres,
            summary = showResponse.summary
        )
    }
}
