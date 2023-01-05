package com.evapharma.limitless.presentation.offers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evapharma.limitless.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor() : ViewModel() {

    private val _offers = MutableLiveData<List<Product>>()
    val offers = _offers

   /* init {
        viewModelScope.launch {
             getOffersUseCase.execute().onSuccess {
                 _offers.value = it
             }
        }
    }*/






}