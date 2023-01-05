package com.evapharma.limitless.domain.usecases.cart

import com.evapharma.limitless.domain.repository.CartRepository
import javax.inject.Inject

class ClearCart @Inject constructor(private val cartRepository: CartRepository){
    suspend fun execute() = cartRepository.clearCart()
}