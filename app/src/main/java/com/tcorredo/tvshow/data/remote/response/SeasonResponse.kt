package com.tcorredo.tvshow.data.remote.response

import com.squareup.moshi.Json

data class SeasonResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "number") val number: Int
)
