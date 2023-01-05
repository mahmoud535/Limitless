package com.evapharma.limitless.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.ItemBannerBinding
import com.evapharma.limitless.domain.model.Banner
import com.evapharma.limitless.presentation.home.DiffUtils.BannerDiffCallback
import com.evapharma.limitless.presentation.home.OnItemClickListener


class SliderAdapter(private val onItemClick: OnItemClickListener) :
    ListAdapter<Banner, SliderAdapter.MyViewHolder>(BannerDiffCallback()) {

    class MyViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(banner: Banner) {
            binding.sliderImage.setImageResource(banner.drawableImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBannerBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onItemClick.onItemClick(getItem(holder.adapterPosition)) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


}