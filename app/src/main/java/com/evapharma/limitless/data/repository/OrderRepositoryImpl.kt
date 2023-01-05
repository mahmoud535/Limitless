package com.evapharma.limitless.data.repository

import com.evapharma.limitless.data.remote.order.OrderRemoteSource
import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.domain.repository.OrderRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderSource: OrderRemoteSource) :OrderRepository {

    override suspend fun selectBillingAddress(addressId: Int): Result<Boolean> = orderSource.selectBillingAddress(addressId)
    override suspend fun confirmOrder(): Result<Boolean> = orderSource.confirmOrder()
    override suspend fun selectPaymentMethod(): Result<Boolean> = orderSource.selectPaymentMethod()
    override suspend fun getOrderHistory(): Result<List<OrderHistory>> = orderSource.orderHistory()
}