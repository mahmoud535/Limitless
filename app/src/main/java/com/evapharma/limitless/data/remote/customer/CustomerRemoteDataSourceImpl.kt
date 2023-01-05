package com.evapharma.limitless.data.remote.customer

import android.util.Log
import com.evapharma.limitless.data.exception.DataNotAvailableException
import com.evapharma.limitless.data.model.LoginData
import com.evapharma.limitless.data.model.LoginResponse
import com.evapharma.limitless.data.model.User
import com.evapharma.limitless.data.remote.Api
import com.evapharma.limitless.data.remote.login.LoginInstance
import com.evapharma.limitless.data.remote.login.LoginInterface
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.DataLog
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.domain.model.VerifiedOtpResponse
import com.evapharma.limitless.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CustomerRemoteDataSourceImpl @Inject constructor(private val api: Api) :
    CustomerRemoteDataSource {
    override suspend fun login(user: User): Result<LoginData> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response: Response<LoginResponse> = api.login(token = ApiTokenSingleton.API_TOKEN,user)
            Log.d("api code ", response.code().toString())
            response.body()?.let {
                Result.Success(it.data)
            } ?: run {
                Result.Failure(DataNotAvailableException())
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun createNewUser(params: LoginModel):Result<DataLog> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response: Response<DataLog> = api.postPhoneNumber(token = ApiTokenSingleton.API_TOKEN,params)
            Log.d("TAG", "create user Code: ${response.code()}")
            response.body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Failure(DataNotAvailableException())
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
//
//
//        return@withContext try {
//            val retroService = LoginInstance.getLoginInstance().create(LoginInterface::class.java)
//            val call = retroService.postPhoneNumber(user)
//            call.enqueue(object : Callback<DataLog> {
//                override fun onFailure(call: Call<DataLog>, t: Throwable) {
//                    createNewUserLiveData.postValue(null)
//                }
//
//                override fun onResponse(call: Call<DataLog>, response: Response<DataLog>) {
//                    if (response.isSuccessful) {
//                        createNewUserLiveData.postValue(response.body()!!.data)
//                    } else {
//                        createNewUserLiveData.postValue(null)
//                    }
//                }
//
//
//            })
//        }catch (e: Exception) {
//
//        }

    override suspend fun verifyOtp(accessToken: String): Result<VerifiedOtpResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response: Response<VerifiedOtpResponse> = api.getOtp(accessToken)
            Log.d("TAG", "verify otp Code: ${response.code()}")
            response.body()?.let {
                Result.Success(it)
            } ?: run {
                Log.d("api code ", response.code().toString())
                Result.Failure(DataNotAvailableException())

            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }





}