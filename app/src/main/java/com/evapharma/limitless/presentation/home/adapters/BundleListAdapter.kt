package com.evapharma.limitless.presentation.home.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.evapharma.limitless.databinding.*
import com.evapharma.limitless.domain.model.BundleData
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.adapters.bindImage
import com.evapharma.limitless.presentation.home.OnItemClickListener
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show


class BundleListAdapter(
    private var mode: Int,
    private val onItemClick: OnBundleClickListener,
    private val onItemClickListener: OnItemClickListener,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BundleListAdapter.MyViewHolder>() {
    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    private var mProducts: List<BundleData> = ArrayList<BundleData>()
    private var mProductsAll: List<Product> = ArrayList<Product>()

    fun setBundleList(products: List<BundleData>) {
        mProducts = products
        notifyDataSetChanged()
    }

    fun getAllProduct(products: List<Product>) {
        mProductsAll = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val holder = if (mode == 1) {
            val binding =
                ItemBundleHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = HomeViewHolder(binding)
            binding.root.setOnClickListener {
                onItemClick.onItemClick(mProducts[holder.adapterPosition].products[0].id)
            }
            binding.btnAddToCart.setOnClickListener {
                onAddToCartClickHome(mProducts[holder.adapterPosition].products[0], binding)
            }
            holder

        } else {

            val binding =
                ItemViewallBundleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = ViewAllHolder(binding)
            binding.root.setOnClickListener {
                onItemClick.onItemClick(mProductsAll[holder.adapterPosition].id)
            }
            binding.btnAddToCart.setOnClickListener {
                onAddToCartClick(mProductsAll[holder.adapterPosition], binding)
            }
            holder
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mode == 1) {
            val model = mProducts[position].products[0]
            holder.bindData(model)
        } else {
            val product = mProductsAll[position]
            holder.bindData(product)
        }
    }

    override fun getItemCount(): Int {
        return if (mode == 1) mProducts.size
        else mProductsAll.size
    }

    private fun onAddToCartClick(product: Product, binding: ItemViewallBundleBinding) {
        onItemClickListener.onItemAddToCartClick(product.id)
            ?.observe(lifecycleOwner) {
                if (it) {
                    binding.btnAddToCart.visibility = View.INVISIBLE
                    binding.progressBar.show()
                } else {
                    product.addToCart.quantity = 1
                    binding.progressBar.hide()
                    binding.addedToCartTv.show()
                }
            }
    }

    private fun onAddToCartClickHome(product: Product, binding: ItemBundleHomeBinding) {
        onItemClickListener.onItemAddToCartClick(product.id)
            ?.observe(lifecycleOwner) {
                if (it) {
                    binding.btnAddToCart.visibility = View.INVISIBLE
                    binding.progressBar.show()
                } else {
                    product.addToCart.quantity = 1
                    binding.progressBar.hide()
                    binding.addedToCartTv.show()
                }
            }
    }

    fun bindHomeData(binding: ItemBundleHomeBinding, position: Int) {
        val product = mProducts[position].products[0]
        binding.apply {
            bindImage(ivItemImage, product.imageUrl)
            tvTitle.text = product.name
            tvDescription.text = product.shortDescription
            tvCurrentPrice.text = product.currency + " " + product.price
            if (product.hasDiscount) {
                discountPercentTv.text = "Save ${product.discountPercentage}% "
                discountPercentTv.show()
                tvDashboardItemPrice.text = product.currency + " " + product.oldPrice
                tvDashboardItemPrice.show()
                tvDashboardItemPrice.paintFlags =
                    tvDashboardItemPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

        }
    }

    fun bindViewAllData(binding: ItemViewallBundleBinding, product: Product) {
        binding.apply {
            bindImage(ivItemImage, product.imageUrl)
            tvTitle.text = product.name
            tvDescription.text = product.shortDescription
            tvCurrentPrice.text = product.currency + " " + product.price
            if (product.addToCart.quantity > 0) {
                btnAddToCart.visibility = View.INVISIBLE
                addedToCartTv.show()
            } else {
                addedToCartTv.hide()
                btnAddToCart.show()
            }
            if (product.hasDiscount) {
                discountPercentTv.text = "Save ${product.discountPercentage}% "
                discountPercentTv.show()
                tvDashboardItemPrice.text = product.currency + " " + product.oldPrice
                tvDashboardItemPrice.show()
                tvDashboardItemPrice.paintFlags =
                    tvDashboardItemPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

        }
    }


    abstract class MyViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bindData(Product: Product)
    }

    inner class HomeViewHolder(private val binding: ItemBundleHomeBinding) :
        MyViewHolder(binding) {
        override fun bindData(Product: Product) {
            bindHomeData(binding, adapterPosition)
        }
    }

    inner class ViewAllHolder(private val binding: ItemViewallBundleBinding) :
        MyViewHolder(binding) {
        override fun bindData(bundleData: Product) {
            bindViewAllData(binding, bundleData)
        }
    }

    interface OnBundleClickListener {
        fun onItemClick(productId: Int)
    }

//    interface OnItemClickListener {
//        fun <T> onItemClick(item: T)
//        fun onItemAddToCartClick (productId : Int) : LiveData<Boolean>
//    }

}