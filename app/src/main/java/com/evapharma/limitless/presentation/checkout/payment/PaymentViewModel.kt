package com.evapharma.limitless.presentation.checkout.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.usecases.order.SelectPaymentUseCase
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import com.evapharma.limitless.presentation.util.PaymentMethod
import com.evapharma.limitless.presentation.util.PaymentMethod.CASH_ON_DELIVERY
import com.evapharma.limitless.presentation.util.PaymentMethod.CREDIT_CARD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val selectPaymentMethod: SelectPaymentUseCase) : ViewModel() {
    var paymentMethod = CASH_ON_DELIVERY
    private val payment = MutableLiveData<Boolean>()
    val paymentLiveData:LiveData<Boolean> = payment

    fun executePaymentMethod(){
        viewModelScope.launch {
            selectPaymentMethod.execute().onSuccess {
                payment.value = it
            }.onFailure {
                println("payment failed")
            }
        }
    }

}