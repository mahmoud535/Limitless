package com.evapharma.limitless.data.remote.login

import com.evapharma.limitless.domain.model.DataLog
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.domain.model.VerifiedOtpResponse
import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJDdXN0b21lcklkIjoiMTExNjg0IiwiTGFuZ3VhZ2UiOiIxIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiZ3Vlc3QiLCJuYmYiOjE2NjI0NjEyMDQsImV4cCI6MTY2MjU0NzYwNH0.cf7jA0XrMtb_QFfClVk7bTvRB4SN9GiTZzU4KJNbcbY")
    @POST("Customer/login")
    fun postPhoneNumber(@Body params: LoginModel): Call<DataLog>

    @GET("Customer/VerifiedOTP/123456")
    fun getData(@Header("Authorization") accessToken:String):Call<VerifiedOtpResponse>

}