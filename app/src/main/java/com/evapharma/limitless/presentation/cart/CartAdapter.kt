package com.evapharma.limitless.presentation.cart

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.CartItemBinding
import com.evapharma.limitless.domain.model.CartProduct
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.adapters.bindImage
import com.evapharma.limitless.presentation.utils.show
import okhttp3.internal.notify

class CartAdapter(private val listener: SetOnCartItemClick) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    var products: List<CartProduct> = ArrayList<CartProduct>()

    fun submitList(products: List<CartProduct>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CartViewHolder(binding)

        binding.root.setOnClickListener {
            val product = products[holder.adapterPosition]
            listener.onCartItemClicked(product.id)
        }
        binding.subSwitch.setOnCheckedChangeListener { _, b ->
            val product = products[holder.adapterPosition]
            listener.onCancelSubscription(b, product.id)
        }
        binding.increaseQuantity.setOnClickListener {
            val product = products[holder.adapterPosition]
            increaseQuantity(product)
        }
        binding.decreaseQuantity.setOnClickListener {
            val product = products[holder.adapterPosition]
            decreaseQuantity(product)
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val product = products[position]

        holder.binding.apply {

            tvCartProductTitle.text = product.name
            tvTabletsNum.text = "20 Tablets"
            tvProductPrice.text = "${product.currency} ${product.unitPrice}"
            bindImage(imgCartProduct, product.imageUrl)

            if (product.oldPrice != null) {
                tvOldPrice.text = "${product.currency} ${product.oldPrice}"
                tvDiscountPercent.text = calcDiscountPercent(product.oldPrice, product.currentPrice)
                tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                cartDiscount.show()
            }
            if (product.isMonthly) {
                monthlySubView.show()
                subSwitch.isChecked = product.isMonthly
            }
            cartProductQuantity.text = "${product.quantity}"
        }
    }

    private fun calcDiscountPercent(oldPrice: Int, currentPrice: Int) =
        "Save " + ((oldPrice.toFloat() - currentPrice) / oldPrice * 100) + "%"

    private fun increaseQuantity(product: CartProduct) {
        ++product.quantity
        listener.onIncreaseQuantity(product.id, product.quantity)
    }

    private fun decreaseQuantity(product: CartProduct) {
        --product.quantity
        listener.onDecreaseQuantity(product.id, product.quantity)
    }


    override fun getItemCount(): Int = products.size
}