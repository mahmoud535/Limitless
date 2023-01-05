package com.evapharma.limitless.domain.usecases.order

import com.evapharma.limitless.domain.repository.OrderRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class SelectAddressUseCase @Inject constructor(private val orderRepo:OrderRepository) {
    suspend fun execute(addressId:Int):Result<Boolean> = orderRepo.selectBillingAddress(addressId)
}