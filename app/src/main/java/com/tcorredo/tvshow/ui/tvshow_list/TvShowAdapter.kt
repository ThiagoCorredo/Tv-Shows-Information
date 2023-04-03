package com.tcorredo.tvshow.ui.tvshow_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.tcorredo.tvshow.R
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.domain.entity.TvShow.Companion.TV_SHOW_COMPARATOR
import com.tcorredo.tvshow.databinding.ItemListTvShowBinding

class TvShowAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<TvShow, TvShowAdapter.ShowViewHolder>(TV_SHOW_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            ItemListTvShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ShowViewHolder(private val binding: ItemListTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow?, listener: OnItemClickListener) {
            binding.tvShowImage.load(tvShow?.imageMedium) {
                crossfade(true)
                error(R.drawable.ic_broken_image_24dp)
                transformations(RoundedCornersTransformation())
                diskCachePolicy(CachePolicy.ENABLED)
                memoryCachePolicy(CachePolicy.ENABLED)
            }

            itemView.setOnClickListener {
                tvShow?.let { listener.onItemClick(it) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(tvShow: TvShow)
    }
}
