package com.evapharma.limitless.domain.usecases.customer

import com.evapharma.limitless.domain.model.DataLog
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.domain.repository.CustomerRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(val customerRepo:CustomerRepository){
    suspend fun execute(params:LoginModel):Result<DataLog> = customerRepo.createNewUser(params)
}