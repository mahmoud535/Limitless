package com.evapharma.limitless.presentation.cart

interface SetOnCartItemClick {
    fun onCartItemClicked(productId:Int)
    fun onIncreaseQuantity(productId:Int, quantity:Int)
    fun onDecreaseQuantity(productId:Int, quantity:Int)
    fun onCancelSubscription(check: Boolean, productId:Int)
}
