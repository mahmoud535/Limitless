package com.evapharma.limitless.domain.repository

import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.util.Result

interface AddressRepository {

    suspend fun addAddress (address: Address) : Result<String>
    suspend fun getAddresses () : Result<List<AddressWithoutPhoneNumber>>

}