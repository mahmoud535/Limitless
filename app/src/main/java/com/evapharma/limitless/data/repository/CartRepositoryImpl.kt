package com.evapharma.limitless.data.repository

import com.evapharma.limitless.data.remote.cart.CartRemoteSource
import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.domain.repository.CartRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartSource: CartRemoteSource): CartRepository {

    override suspend fun getCart(): Result<Cart> = cartSource.getCart()
    override suspend fun putProduct(cartDetails: CartDetails) = cartSource.putProduct(cartDetails)
    override suspend fun addToCart(cartDetails: CartDetails) = cartSource.addToCart(cartDetails)
    override suspend fun clearCart() = cartSource.clearCart()

}