package com.evapharma.limitless.data.remote.order

import com.evapharma.limitless.data.exception.DataNotAvailableException
import com.evapharma.limitless.data.model.OrderHistoryResponse
import com.evapharma.limitless.data.remote.Api
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.OrderHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class OrderRemoteSourceImpl @Inject constructor(val api: Api): OrderRemoteSource {

    override suspend fun orderHistory(): Result<List<OrderHistory>> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.ordersHistory(ApiTokenSingleton.API_TOKEN)
            println("order 1: ${response.code()}")
            if (response.code() in 200..298){
                response.body()?.let {
                    Result.Success(it.data)
                } ?: Result.Failure(DataNotAvailableException())
            }else Result.Failure(DataNotAvailableException())
        }catch (ex:Exception){
            Result.Failure(ex)
        }
    }

    override suspend fun selectBillingAddress(addressId: Int):Result<Boolean> = withContext(Dispatchers.IO){
        return@withContext try {
            api.selectBillingAddress(ApiTokenSingleton.API_TOKEN, addressId)
            Result.Success(true)
        }catch (exp:Exception){
            println(exp.toString())
            Result.Failure(exp)
        }
    }

    override suspend fun confirmOrder():Result<Boolean> = withContext(Dispatchers.IO){
        return@withContext  try {
            api.confirmOrder(ApiTokenSingleton.API_TOKEN)
            Result.Success(true)
        }catch (exp:Exception){
            Result.Failure(exp)
        }
    }


    override suspend fun selectPaymentMethod():Result<Boolean> = withContext(Dispatchers.IO){
        return@withContext try {
            api.selectPaymentMethod(ApiTokenSingleton.API_TOKEN)
            Result.Success(true)
        }catch (exp:Exception){
            Result.Failure(exp)
        }
    }

}