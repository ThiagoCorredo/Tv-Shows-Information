package com.tcorredo.tvshow.data.remote.response

import com.squareup.moshi.Json

data class SearchTvShowResponse(
    @Json(name = "score") val score: Double,
    @Json(name = "show") val tvShow: ShowResponse,
)