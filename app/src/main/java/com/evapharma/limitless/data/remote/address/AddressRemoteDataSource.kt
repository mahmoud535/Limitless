package com.evapharma.limitless.data.remote.address

import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.model.AddressesResponse
import com.evapharma.limitless.domain.util.Result
import retrofit2.Response

interface AddressRemoteDataSource {

    suspend fun addAddress (address: Address) : Result<String>
    suspend fun getAddresses () : Result<List<AddressWithoutPhoneNumber>>

}