package com.evapharma.limitless.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.ItemCarouselBinding
import com.evapharma.limitless.domain.model.Carousel
import com.evapharma.limitless.presentation.home.OnItemClickListener
import com.evapharma.limitless.presentation.home.DiffUtils.CarouselDiffCallback

class CarouselAdapter(private val onItemClick: OnItemClickListener) :
    ListAdapter<Carousel, CarouselAdapter.MyViewHolder>(CarouselDiffCallback()) {

    class MyViewHolder(private val binding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(carousel: Carousel) {
            binding.carouselImage.setImageResource(carousel.drawableImage)
            binding.carouselNameTv.text = carousel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarouselBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onItemClick.onItemClick(getItem(holder.adapterPosition)) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}