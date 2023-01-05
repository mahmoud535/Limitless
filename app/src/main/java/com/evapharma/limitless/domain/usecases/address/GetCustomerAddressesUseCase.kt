package com.evapharma.limitless.domain.usecases.address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.repository.AddressRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetCustomerAddressesUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    suspend fun execute(): Result<List<AddressWithoutPhoneNumber>> =
        addressRepository.getAddresses()
}