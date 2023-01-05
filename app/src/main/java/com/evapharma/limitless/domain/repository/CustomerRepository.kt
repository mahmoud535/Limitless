package com.evapharma.limitless.domain.repository

import com.evapharma.limitless.data.model.LoginData
import com.evapharma.limitless.data.model.User
import com.evapharma.limitless.domain.model.Data
import com.evapharma.limitless.domain.model.DataLog
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.domain.model.VerifiedOtpResponse
import com.evapharma.limitless.domain.util.Result
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import javax.inject.Inject

interface CustomerRepository  {

    suspend fun login (user: User) : Result<LoginData>
    suspend fun createNewUser( params: LoginModel): Result<DataLog>
    suspend fun verifyOtp(accessToken:String): Result<VerifiedOtpResponse>

}