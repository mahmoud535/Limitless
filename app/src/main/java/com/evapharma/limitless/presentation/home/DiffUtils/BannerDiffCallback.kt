package com.evapharma.limitless.presentation.home.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.evapharma.limitless.domain.model.Banner

class BannerDiffCallback  : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner) = oldItem == newItem
}