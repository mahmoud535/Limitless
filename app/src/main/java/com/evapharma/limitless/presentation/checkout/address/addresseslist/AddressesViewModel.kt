package com.evapharma.limitless.presentation.checkout.address.addresseslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.usecases.address.GetCustomerAddressesUseCase
import com.evapharma.limitless.domain.usecases.order.SelectAddressUseCase
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val getCustomerAddressesUseCase: GetCustomerAddressesUseCase,
    private val selectAddressUseCase: SelectAddressUseCase
) :
    ViewModel() {

    private val _addressesList = MutableLiveData<List<AddressWithoutPhoneNumber>>()
    val addressesList: LiveData<List<AddressWithoutPhoneNumber>> = _addressesList

    private val address = MutableLiveData<Boolean>()
    val addressLiveData: LiveData<Boolean> = address

    private val _anyAddressExist = MutableLiveData<Boolean>()
    val anyAddressExist: LiveData<Boolean> = _anyAddressExist

    init {
        getAddresses()
    }

    fun executeBillingAddress(addressId: Int){
        viewModelScope.launch {
            selectAddressUseCase.execute(addressId).onSuccess {
                address.value = it
            }.onFailure {
                println("address failed")
            }
        }
    }

    fun getAddresses() {
        viewModelScope.launch {
            getCustomerAddressesUseCase.execute().onSuccess {
                _addressesList.value = it
                _anyAddressExist.value = it.isNotEmpty()
                Log.d("add address: ", "${it.size} addresses")
            }.onFailure {
                _anyAddressExist.value = false
                Log.d("add address: ", "${it.message}")
            }
        }
    }

}