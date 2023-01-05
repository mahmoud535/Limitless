package com.evapharma.limitless.data.repository

import com.evapharma.limitless.data.model.LoginData
import com.evapharma.limitless.data.model.User
import com.evapharma.limitless.domain.model.DataLog
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.domain.model.VerifiedOtpResponse
import com.evapharma.limitless.data.remote.customer.CustomerRemoteDataSource
import com.evapharma.limitless.domain.repository.CustomerRepository
import com.evapharma.limitless.domain.util.Result
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(private val customerRemoteDataSource: CustomerRemoteDataSource) :
    CustomerRepository {
    override suspend fun login(user: User): Result<LoginData> = customerRemoteDataSource.login(user)

    override suspend fun createNewUser(params: LoginModel): Result<DataLog> = customerRemoteDataSource.createNewUser(params)

    override suspend fun verifyOtp(accessToken: String): Result<VerifiedOtpResponse> = customerRemoteDataSource.verifyOtp(accessToken)
}