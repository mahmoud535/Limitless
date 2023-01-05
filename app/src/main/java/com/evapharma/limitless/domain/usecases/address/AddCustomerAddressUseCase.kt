package com.evapharma.limitless.domain.usecases.address

import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.repository.AddressRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class AddCustomerAddressUseCase @Inject constructor(private val addressRepository: AddressRepository){
    suspend fun execute (address: Address): Result<String> = addressRepository.addAddress(address)
}