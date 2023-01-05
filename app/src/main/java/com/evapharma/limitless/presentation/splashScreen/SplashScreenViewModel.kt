package com.evapharma.limitless.presentation.splashScreen

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.data.local.PreferenceManager
import com.evapharma.limitless.data.model.User
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.usecases.customer.LoginUseCase
import com.evapharma.limitless.domain.usecases.sp.GetLocationState
import com.evapharma.limitless.domain.usecases.sp.GetString
import com.evapharma.limitless.domain.usecases.sp.PutString
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import com.evapharma.limitless.presentation.util.CUSTOMER_ACCESS_TOKEN_KEY
import com.evapharma.limitless.presentation.util.Constants
import com.evapharma.limitless.presentation.util.Constants.Companion.KEY_LOCATION_STATE
import com.evapharma.limitless.presentation.util.GUEST_ACCESS_TOKEN_KEY
import com.evapharma.limitless.presentation.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getLocationState: GetLocationState,
    private val loginUseCase: LoginUseCase,
    private val getString: GetString,
    private val putString: PutString,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    init {
        isAuthenticated()
    }

    private lateinit var mPreferenceManager: PreferenceManager

    fun getLocationState(context: Context): Boolean {
        mPreferenceManager = PreferenceManager(context)
        return getLocationState.execute(KEY_LOCATION_STATE)
    }

    private fun isAuthenticated() {
        getString.execute(CUSTOMER_ACCESS_TOKEN_KEY)?.let {
            if (it.isNotEmpty()) {
                ApiTokenSingleton.API_TOKEN = it
                _loading.value = false
            } else {
                loginAsGuest()
            }
        } ?: run {
            loginAsGuest()
        }

    }

    private fun loginAsGuest() {
        viewModelScope.launch {
            loginUseCase.execute(User(isGuest = true)).onSuccess {
                ApiTokenSingleton.API_TOKEN = it.accessToken
                putString.execute(GUEST_ACCESS_TOKEN_KEY, ApiTokenSingleton.API_TOKEN)
                _loading.value = false
            }.onFailure {
                _loading.value = false
            }
        }
    }
}