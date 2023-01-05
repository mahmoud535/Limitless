package com.evapharma.limitless.domain.usecases.order

import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.domain.repository.OrderRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class OrderHistoryUseCase @Inject constructor(private val orderRepo:OrderRepository) {

    suspend fun execute():Result<List<OrderHistory>> = orderRepo.getOrderHistory()
}