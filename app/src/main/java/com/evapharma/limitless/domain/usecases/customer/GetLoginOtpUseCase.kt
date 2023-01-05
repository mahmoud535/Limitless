package com.evapharma.limitless.domain.usecases.customer


import com.evapharma.limitless.domain.model.VerifiedOtpResponse
import com.evapharma.limitless.domain.repository.CustomerRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class GetLoginOtpUseCase @Inject constructor(private val customerRepository: CustomerRepository) {
    suspend fun execute (token: String): Result<VerifiedOtpResponse> = customerRepository.verifyOtp(token)
}