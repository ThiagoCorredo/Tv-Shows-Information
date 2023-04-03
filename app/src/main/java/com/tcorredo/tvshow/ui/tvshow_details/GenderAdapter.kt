package com.tcorredo.tvshow.ui.tvshow_details

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tcorredo.tvshow.databinding.ItemListGenderBinding
import java.util.*

class GenderAdapter(private val genderList: List<String>) :
    RecyclerView.Adapter<GenderAdapter.GenderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenderViewHolder {
        return GenderViewHolder(
            ItemListGenderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenderViewHolder, position: Int) {
        holder.bindView(genderList[position])
    }

    override fun getItemCount(): Int = genderList.size

    class GenderViewHolder(private val binding: ItemListGenderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(gender: String) {
            binding.run {
                val random = Random()
                val color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                tvShowGender.setBackgroundColor(color)
                tvShowGender.text = gender
            }
        }
    }
}