package com.tcorredo.tvshow.ui.actor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.tcorredo.tvshow.R
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.databinding.ItemListActorBinding

class ActorAdapter :
    PagingDataAdapter<Actor, ActorAdapter.ActorViewHolder>(Actor.ACTOR_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            ItemListActorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ActorViewHolder(private val binding: ItemListActorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(actor: Actor?) {
            binding.run {
                actorImage.load(actor?.image) {
                    crossfade(true)
                    error(R.drawable.ic_person_24dp)
                    transformations(RoundedCornersTransformation())
                    diskCachePolicy(CachePolicy.ENABLED)
                    memoryCachePolicy(CachePolicy.ENABLED)
                }
                actorName.text = actor?.name
            }
        }
    }
}