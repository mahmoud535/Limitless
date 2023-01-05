package com.evapharma.limitless.presentation.home.DiffUtils
import androidx.recyclerview.widget.DiffUtil
import com.evapharma.limitless.domain.model.Carousel

class CarouselDiffCallback  : DiffUtil.ItemCallback<Carousel>() {
    override fun areItemsTheSame(oldItem: Carousel, newItem: Carousel): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Carousel, newItem: Carousel) = oldItem == newItem
}