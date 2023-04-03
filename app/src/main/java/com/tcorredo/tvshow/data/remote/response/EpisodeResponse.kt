package com.tcorredo.tvshow.data.remote.response

import com.squareup.moshi.Json

data class EpisodeResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "image") val images: ShowImage?,
)
