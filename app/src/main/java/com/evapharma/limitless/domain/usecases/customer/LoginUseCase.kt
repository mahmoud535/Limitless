package com.evapharma.limitless.domain.usecases.customer

import com.evapharma.limitless.data.model.LoginData
import com.evapharma.limitless.data.model.User
import com.evapharma.limitless.domain.repository.CustomerRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val customerRepository: CustomerRepository) {
    suspend fun execute (user: User): Result<LoginData> = customerRepository.login(user)
}