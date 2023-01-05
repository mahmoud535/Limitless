package com.evapharma.limitless.data.remote.cart

import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.data.exception.DataNotAvailableException
import com.evapharma.limitless.data.remote.Api
import com.evapharma.limitless.data.util.ApiTokenSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.evapharma.limitless.domain.util.Result

class CartRemoteSourceImpl @Inject constructor(val api: Api) : CartRemoteSource {

    override suspend fun getCart(): Result<Cart> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getCarts(token = ApiTokenSingleton.API_TOKEN)
            println("Code: ${response.code()}")
            println("data: ${response.body()!!.data!!}")
            if (response.code() in 200..298) {
                response.body()?.let{
                    it.data?.let{
                        Result.Success(it)
                    }
                } ?: Result.Failure(DataNotAvailableException())
            } else {
                Result.Failure(DataNotAvailableException())
            }
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

    override suspend fun putProduct(cartDetails: CartDetails) {
        withContext(Dispatchers.IO) {
            try {
                api.putProduct(token = ApiTokenSingleton.API_TOKEN,cartDetails)
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun addToCart(cartDetails: CartDetails) {
        withContext(Dispatchers.IO) {
            try {
                api.addToCart(token = ApiTokenSingleton.API_TOKEN,cartDetails)
            } catch (e: Exception) {

            }
        }
    }

    override suspend fun clearCart() {
        withContext(Dispatchers.IO) {
            try {
                api.clearCart(token = ApiTokenSingleton.API_TOKEN)
            } catch (e: Exception) {
            }
        }
    }
}