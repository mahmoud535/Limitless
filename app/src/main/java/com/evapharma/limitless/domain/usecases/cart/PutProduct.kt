package com.evapharma.limitless.domain.usecases.cart

import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.repository.CartRepository
import javax.inject.Inject

class PutProduct @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(cartDetails: CartDetails) = cartRepository.putProduct(cartDetails)
}