package com.tcorredo.tvshow.data.domain.entity

import androidx.recyclerview.widget.DiffUtil

data class Actor(
    val id: Long,
    val name: String,
    val image: String
) {
    companion object {
        val ACTOR_COMPARATOR = object : DiffUtil.ItemCallback<Actor>() {
            override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem == newItem
            }
        }
    }
}
