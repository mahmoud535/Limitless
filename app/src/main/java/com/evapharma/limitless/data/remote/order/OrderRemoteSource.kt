package com.evapharma.limitless.data.remote.order
import com.evapharma.limitless.data.model.OrderHistoryResponse
import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.domain.util.Result

interface OrderRemoteSource {

    suspend fun selectBillingAddress(addressId: Int):Result<Boolean>
    suspend fun confirmOrder():Result<Boolean>
    suspend fun selectPaymentMethod():Result<Boolean>
    suspend fun orderHistory():Result<List<OrderHistory>>

}