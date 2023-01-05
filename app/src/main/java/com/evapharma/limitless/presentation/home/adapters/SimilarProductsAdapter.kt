package com.evapharma.limitless.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.SimilarProductItemBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.model.ProductOverview
import com.evapharma.limitless.presentation.home.DiffUtils.ProductOverviewDiffUtil
import com.evapharma.limitless.presentation.home.OnItemClickListener

class SimilarProductsAdapter(private val onItemClick: SimilarProductsAdapter.OnSimilarProductItemClickListener) :
    ListAdapter<ProductOverview, SimilarProductsAdapter.MyViewHolder>(ProductOverviewDiffUtil()) {


    class MyViewHolder(private val binding: SimilarProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(productOverview: ProductOverview) {
            binding.product = productOverview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SimilarProductItemBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onItemClick.onClick(getItem(holder.adapterPosition).id) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


    interface OnSimilarProductItemClickListener {
        fun onClick(productId : Int)
    }


}