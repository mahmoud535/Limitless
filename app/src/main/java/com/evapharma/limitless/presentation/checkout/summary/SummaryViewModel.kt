package com.evapharma.limitless.presentation.checkout.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.usecases.order.ConfirmOrderUseCase
import com.evapharma.limitless.domain.usecases.order.SelectPaymentUseCase
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(private val confirmOrder: ConfirmOrderUseCase) : ViewModel()  {

    private val summary = MutableLiveData<Boolean>()
    val summaryLiveData: LiveData<Boolean> = summary

    fun executeConfirmOrder(){
        viewModelScope.launch {
            confirmOrder.execute().onSuccess {
                summary.value = it
            }.onFailure {
                println("summary failed")
            }
        }
    }

}