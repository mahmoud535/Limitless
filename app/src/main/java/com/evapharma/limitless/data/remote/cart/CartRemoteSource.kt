package com.evapharma.limitless.data.remote.cart

import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.domain.util.Result

interface CartRemoteSource {
    suspend fun getCart():Result<Cart>
    suspend fun putProduct(cartDetails: CartDetails)
    suspend fun addToCart(cartDetails: CartDetails)
    suspend fun clearCart()
}