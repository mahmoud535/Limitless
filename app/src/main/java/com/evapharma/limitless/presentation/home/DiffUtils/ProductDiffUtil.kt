package com.evapharma.limitless.presentation.home.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.model.ProductOverview
import com.evapharma.limitless.domain.model.ProductTag

class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}