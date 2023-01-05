package com.evapharma.limitless.presentation.loginandsignup.signinusingmobilefragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.data.remote.login.LoginInstance
import com.evapharma.limitless.data.remote.login.LoginInterface
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.domain.model.VerifiedOtpResponse
import com.evapharma.limitless.domain.usecases.customer.CreateUserUseCase
import com.evapharma.limitless.domain.usecases.customer.GetLoginOtpUseCase
import com.evapharma.limitless.domain.usecases.sp.PutString
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val createUser: CreateUserUseCase,
    val getLoginOtp: GetLoginOtpUseCase,
    val putString: PutString,
) : ViewModel() {

    private var createNewUserLiveData: MutableLiveData<LoginModel?> = MutableLiveData()
    private var verifiedOtpLiveData: MutableLiveData<VerifiedOtpResponse?> = MutableLiveData()

    val userLiveData: LiveData<LoginModel?> = createNewUserLiveData
    val loginOtpLiveData: LiveData<VerifiedOtpResponse?> = verifiedOtpLiveData

    suspend fun createUserResult(params:LoginModel){
        createUser.execute(params).onSuccess {
            createNewUserLiveData.postValue(it.data!!)
        }.onFailure {
            createNewUserLiveData.postValue(null)
        }
    }

    suspend fun getOtpResult(token:String){
        getLoginOtp.execute(token).onSuccess {
            verifiedOtpLiveData.postValue(it)
        }.onFailure {
            verifiedOtpLiveData.postValue(null)
        }
    }

    fun executeNewUser(params:LoginModel){
        viewModelScope.launch {
            createUserResult(params)
        }
    }

    fun executeLoginOtp(token:String){
        viewModelScope.launch {
            getOtpResult(token)
        }
    }

    fun verifyOtp(accessToken: String): MutableLiveData<VerifiedOtpResponse?> {
        val retroService = LoginInstance.getLoginInstance().create(LoginInterface::class.java)
        val call = retroService.getData(accessToken)
        call.enqueue(object : Callback<VerifiedOtpResponse> {
            override fun onResponse(
                call: Call<VerifiedOtpResponse>,
                response: Response<VerifiedOtpResponse>
            ) {
                verifiedOtpLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<VerifiedOtpResponse>, t: Throwable) {
                Log.d("otpError", t.message.toString())
            }

        })

        return verifiedOtpLiveData

    }
}