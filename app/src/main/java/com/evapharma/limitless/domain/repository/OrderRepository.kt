package com.evapharma.limitless.domain.repository

import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.domain.util.Result

interface OrderRepository {

    suspend fun selectBillingAddress(addressId: Int): Result<Boolean>
    suspend fun confirmOrder(): Result<Boolean>
    suspend fun selectPaymentMethod(): Result<Boolean>
    suspend fun getOrderHistory():Result<List<OrderHistory>>

}