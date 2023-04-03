package com.tcorredo.tvshow.data.remote.response

import com.squareup.moshi.Json

data class ShowResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "image") val images: ShowImage?,
    @Json(name = "schedule") val schedule: Schedule,
    @Json(name = "genres") val genres: List<String>,
    @Json(name = "summary") val summary: String
)

data class ShowImage(
    @Json(name = "medium") val medium: String,
    @Json(name = "original")  val original: String
)

data class Schedule(
    @Json(name = "time") val time: String,
    @Json(name = "days") val days: List<String>
)
