package com.evapharma.limitless.domain.usecases.cart

import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.domain.repository.CartRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetCart @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute():Result<Cart> = cartRepository.getCart()
}