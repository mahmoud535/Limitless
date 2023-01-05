package com.evapharma.limitless.data.repository

import com.evapharma.limitless.data.remote.address.AddressRemoteDataSource
import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.repository.AddressRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(private val addressRemoteDataSource: AddressRemoteDataSource) :
    AddressRepository {
    override suspend fun addAddress(address: Address): Result<String> =
        addressRemoteDataSource.addAddress(address)

    override suspend fun getAddresses(): Result<List<AddressWithoutPhoneNumber>> =
        addressRemoteDataSource.getAddresses()
}