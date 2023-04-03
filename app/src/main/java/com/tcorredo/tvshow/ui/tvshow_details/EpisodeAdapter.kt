package com.tcorredo.tvshow.ui.tvshow_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.tcorredo.tvshow.R
import com.tcorredo.tvshow.data.domain.entity.TvShowEpisode
import com.tcorredo.tvshow.databinding.ItemListEpisodeBinding

class EpisodeAdapter :
    ListAdapter<TvShowEpisode, EpisodeAdapter.EpisodeItemViewHolder>(TvShowEpisode.EPISODE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeItemViewHolder {
        return EpisodeItemViewHolder(
            ItemListEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeItemViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class EpisodeItemViewHolder(private val binding: ItemListEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(tvShowEpisode: TvShowEpisode) {
            binding.run {
                episodeImage.load(tvShowEpisode.episodeImage) {
                    crossfade(true)
                    error(R.drawable.ic_broken_image_24dp)
                    transformations(RoundedCornersTransformation())
                    diskCachePolicy(CachePolicy.ENABLED)
                    memoryCachePolicy(CachePolicy.ENABLED)
                }

                episodeName.text = tvShowEpisode.episodeName
            }
        }
    }
}