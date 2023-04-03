package com.tcorredo.tvshow.data.domain.entity

import androidx.recyclerview.widget.DiffUtil

data class TvShowDetails(
    val tvShow: TvShow?,
    val tvShowSeasons: List<TvShowSeason>
)

data class TvShowSeason(
    val id: Long,
    val seasonNumber: Int,
    val seasonEpisodes: List<TvShowEpisode>
)

data class TvShowEpisode(
    val id: Long,
    val episodeName: String,
    val episodeImage: String
) {
    companion object {
        val EPISODE_COMPARATOR = object : DiffUtil.ItemCallback<TvShowEpisode>() {
            override fun areItemsTheSame(oldItem: TvShowEpisode, newItem: TvShowEpisode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShowEpisode,
                newItem: TvShowEpisode
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}