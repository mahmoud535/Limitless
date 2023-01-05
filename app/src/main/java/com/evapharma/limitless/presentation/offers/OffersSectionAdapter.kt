package com.evapharma.limitless.presentation.offers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.ItemProductGridBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.home.DiffUtils.ProductDiffUtil
import com.evapharma.limitless.presentation.home.OnItemClickListener
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show

class OffersSectionAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val lifecycleOwner: LifecycleOwner
) :
    ListAdapter<Product, OffersSectionAdapter.MyViewHolder>(ProductDiffUtil()) {


    class MyViewHolder(private val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(offer: Product) {
            binding.product = offer
            if (offer.addToCart.quantity > 0) {
                binding.addToCartBtn.visibility = View.INVISIBLE
                binding.addedToCartTv.show()
            } else {
                binding.addedToCartTv.hide()
                binding.addToCartBtn.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductGridBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onItemClickListener.onItemClick(getItem(holder.adapterPosition)) }
        binding.addToCartBtn.setOnClickListener {
            onAddToCartClick(getItem(holder.adapterPosition), binding)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 4) 4 else super.getItemCount()
    }

    private fun onAddToCartClick(product: Product, binding: ItemProductGridBinding) {
        onItemClickListener.onItemAddToCartClick(product.id)
            ?.observe(lifecycleOwner) {
                if (it) {
                    binding.addToCartBtn.visibility = View.INVISIBLE
                    binding.progressBar.show()
                    Log.d("cart:", "loading progress bar")
                } else {
                    product.addToCart.quantity = 1
                    binding.progressBar.hide()
                    binding.addedToCartTv.show()
                    Log.d("cart:", "added to cart")
                }
            }
    }

}