package com.tcorredo.tvshow.data.domain.entity

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: Long,
    val name: String,
    val imageMedium: String,
    val imageOriginal: String,
    val scheduleDays: List<String>,
    val scheduleTime: String,
    val genres: List<String>,
    val summary: String
) : Parcelable {
    companion object {
        val TV_SHOW_COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}
