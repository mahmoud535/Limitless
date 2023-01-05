package com.evapharma.limitless.presentation.checkout.address.bottomsheet

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.usecases.address.AddCustomerAddressUseCase
import com.evapharma.limitless.domain.usecases.address.GetReadableAddressFromLatLngUseCase
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val addCustomerAddressUseCase: AddCustomerAddressUseCase,
    private val getReadableAddressFromLatLngUseCase: GetReadableAddressFromLatLngUseCase
) :
    ViewModel() {


    private val _address = MutableLiveData<Map<String, String>>()
    var address: LiveData<Map<String, String>> = _address

    private val _resultMessage = MutableLiveData<String>()
    var resultMessage: LiveData<String> = _resultMessage

    @SuppressLint("MissingPermission")
    fun getLastLocation(fusedClient: FusedLocationProviderClient): Task<Location> {
        return fusedClient.lastLocation

    }

    fun mapLocationToReadableAddress(location: Location, context: Context) {
        _address.value = getReadableAddressFromLatLngUseCase.execute(location, context)
    }


    fun saveAddress(
        addressName: String,
        city: String,
        area: String,
        street: String,
        mobileNumber: String,
        buildingNum: Int,
        apartmentNum: Int,
        isDefault : Boolean
    ) {
        val address = Address(
            addressName = addressName,
            city = city,
            area = area,
            street = street,
            buildingNumber = buildingNum,
            apartmentNumber = apartmentNum,
            mobileNumber = mobileNumber,
            firstName = "Mina",
            lastName = "Ashraf",
            email = ""
        )
        viewModelScope.launch {
            addCustomerAddressUseCase.execute(address).onSuccess {
                _resultMessage.value = it
            }.onFailure {
                _resultMessage.value = it.message
            }
        }
    }

}