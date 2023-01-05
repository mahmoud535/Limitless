package com.evapharma.limitless.data.remote.address

import android.content.Context
import com.evapharma.limitless.R
import com.evapharma.limitless.data.exception.DataNotAvailableException
import com.evapharma.limitless.data.remote.Api
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressRemoteDataSourceImpl @Inject constructor(
    private val api: Api,
    @ApplicationContext private val context: Context
) :
    AddressRemoteDataSource {
    override suspend fun addAddress(address: Address): Result<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                api.addCustomerAddress(token = ApiTokenSingleton.API_TOKEN,address).body()?.let {
                    if (it.data)
                        Result.Success(it.message)
                    else
                        Result.Failure(Exception(it.message))
                }
                    ?: Result.Failure(Exception(context.getString(R.string.address_adding_failure_message)))

            } catch (e: Exception) {
                Result.Failure(e)
            }
        }


    override suspend fun getAddresses(): Result<List<AddressWithoutPhoneNumber>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                api.getCustomerAddresses(token = ApiTokenSingleton.API_TOKEN).body()?.let {
                    Result.Success(it.data)
                } ?: Result.Failure(DataNotAvailableException())
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
}