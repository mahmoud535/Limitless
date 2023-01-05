package com.evapharma.limitless.presentation.home.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.model.ProductOverview
import com.evapharma.limitless.domain.model.ProductTag

class ProductOverviewDiffUtil : DiffUtil.ItemCallback<ProductOverview>() {
    override fun areItemsTheSame(oldItem: ProductOverview, newItem: ProductOverview): Boolean = oldItem === newItem

    override fun areContentsTheSame(oldItem: ProductOverview, newItem: ProductOverview): Boolean =
        oldItem == newItem
}