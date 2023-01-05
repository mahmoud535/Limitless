package com.evapharma.limitless.presentation.category

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.evapharma.limitless.databinding.CategoryItemBinding
import com.evapharma.limitless.databinding.CategoryItemGridBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.model.ProductsByCategory
import com.evapharma.limitless.presentation.adapters.bindImage
import com.evapharma.limitless.presentation.util.LayoutManager
import com.evapharma.limitless.presentation.util.LayoutManager.GRID
import com.evapharma.limitless.presentation.util.LayoutManager.LIST
import com.evapharma.limitless.presentation.utils.show

class CategoryDetailAdapter(
    private val listener:SetOnProductClick,
    private val manager: LayoutManager,
    private val products: ProductsByCategory
) : RecyclerView.Adapter<CategoryDetailAdapter.CategoryDetailViewHolder>() {

    abstract class CategoryDetailViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bindData(product: Product)
    }

    inner class GridViewHolder(private val binding: CategoryItemGridBinding) :
        CategoryDetailViewHolder(binding) {
        override fun bindData(product: Product) {
            setGridData(binding, product)
        }
    }

    inner class ListViewHolder(private val binding: CategoryItemBinding) :
        CategoryDetailViewHolder(binding) {
        override fun bindData(product: Product) {
            setListData(binding, product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDetailViewHolder {

        val holder = when (manager) {
            LIST -> {
                val binding =
                    CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val holder = ListViewHolder(binding)
                binding.root.setOnClickListener{
                    listener.onProductClicked(products.products[holder.adapterPosition].id)
                }
                holder
            }
            GRID -> {
                val binding = CategoryItemGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val holder = GridViewHolder(binding)
                binding.root.setOnClickListener{
                    listener.onProductClicked(products.products[holder.adapterPosition].id)
                }
                holder
            }
        }
        return holder
    }

    fun setListData(binding:CategoryItemBinding, product: Product) {

            binding.apply {
                tvCategoryPrice.text = "${product.currency} ${product.price}"
                tvCategoryProductTitle.text = product.name
                bindImage(binding.imgCategoryProduct, product.imageUrl)
                if (product.hasDiscount) {
                    categoryDiscountGroup.show()
                    tvCategoryOldPrice.text = "${product.currency} ${product.oldPrice}"
                    tvCategoryOldPrice.paintFlags = tvCategoryOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvCategoryDiscountPercent.text = "Save ${product.discountPercentage}%"
                }
                tvTabletsNum.text = "10 tablets"
            }
    }

    fun setGridData(binding:CategoryItemGridBinding, product: Product) {

            binding.apply {
                tvCategoryPrice.text = "${product.price} ${product.currency}"
                tvCategoryProductTitle.text = product.name
                bindImage(binding.imgCategoryProduct, product.imageUrl)
                if (product.hasDiscount) {
                    categoryDiscountGroup.show()
                    tvCategoryOldPrice.text = "${product.currency} ${product.oldPrice}"
                    tvCategoryOldPrice.paintFlags = tvCategoryOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvCategoryDiscountPercent.text = "Save ${product.discountPercentage}%"
                }
                tvTabletsNum.text = "10 tablets"
            }
    }

    override fun onBindViewHolder(holder: CategoryDetailViewHolder, position: Int) {
        val product = products.products[position]
        holder.bindData(product)
    }

    override fun getItemCount(): Int {
        return products.products.size
    }

}