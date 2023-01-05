package com.evapharma.limitless.presentation.checkout

import androidx.lifecycle.ViewModel
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.domain.model.Summary

class CheckoutViewModel : ViewModel() {

    var selectedAddress : AddressWithoutPhoneNumber? = null
    lateinit var summary : Summary



}