package com.evapharma.limitless.domain.repository
import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.domain.util.Result

interface CartRepository {

    suspend fun getCart():Result<Cart>
    suspend fun putProduct(cartDetails: CartDetails)
    suspend fun addToCart(cartDetails: CartDetails)
    suspend fun clearCart()

}