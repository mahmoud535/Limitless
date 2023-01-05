package com.evapharma.limitless.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.ItemProductGridBinding
import com.evapharma.limitless.databinding.ItemProductListBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.home.DiffUtils.ProductDiffUtil
import com.evapharma.limitless.presentation.home.OnItemClickListener
import com.evapharma.limitless.presentation.utils.hide

class OffersAdapter(private val onItemClick: OffersAdapter.OnOfferClickListener) :
    ListAdapter<Product, OffersAdapter.MyViewHolder>(ProductDiffUtil()) {

    private var viewType = ViewType.GRID

    abstract class MyViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bindData(offer: Product)
    }

    class GridViewHolder(private val binding: ItemProductGridBinding) : MyViewHolder(binding) {
        override fun bindData(offer: Product) {
            binding.product = offer
            binding.addToCartBtn.hide()
        }
    }

    class ListViewHolder(private val binding: ItemProductListBinding) : MyViewHolder(binding) {
        override fun bindData(offer: Product) {
            binding.product = offer
        }
    }

    fun setViewType(viewType: ViewType) {
        this.viewType = viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding
        val holder = when (this.viewType) {
            ViewType.GRID -> {
                binding = ItemProductGridBinding.inflate(inflater, parent, false)
                GridViewHolder(binding)
            }
            else -> {
                binding = ItemProductListBinding.inflate(inflater, parent, false)
                ListViewHolder(binding)
            }
        }
        binding.root.setOnClickListener { onItemClick.onItemClick(getItem(holder.adapterPosition).id) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))

    }

    enum class ViewType {
        GRID, LIST
    }


    interface OnOfferClickListener {
       fun onItemClick(productId : Int)
    }

}