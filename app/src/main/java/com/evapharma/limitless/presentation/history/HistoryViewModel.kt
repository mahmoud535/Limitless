package com.evapharma.limitless.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.domain.usecases.order.OrderHistoryUseCase
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val orderHistory: OrderHistoryUseCase) : ViewModel() {

    private val orders = MutableLiveData<List<OrderHistory>>()
    val ordersLiveData: LiveData<List<OrderHistory>> = orders

   private suspend fun executeOrderHistory(){
            orderHistory.execute().onSuccess {
                orders.value = it
            }.onFailure {

            }
    }

    fun refreshOrders(){
        viewModelScope.launch {
            executeOrderHistory()
        }
    }

}