package com.evapharma.limitless.presentation.productdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.model.ProductOverview
import com.evapharma.limitless.domain.usecases.catalog.GetProductDetailsUseCase
import com.evapharma.limitless.domain.usecases.cart.AddToCartUseCase
import com.evapharma.limitless.domain.usecases.cart.PutProduct
import com.evapharma.limitless.domain.usecases.sp.GetString
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import com.evapharma.limitless.presentation.util.CUSTOMER_ACCESS_TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val updateCartUserCase: PutProduct,
    private val getStringSp: GetString

) : ViewModel() {

    private val _productDetailsLiveData = MutableLiveData<Product>()
    val productDetailsLiveData: LiveData<Product> = _productDetailsLiveData
    private val _quantityLiveData = MutableLiveData<Int>()
    val quantityLiveData: LiveData<Int> = _quantityLiveData

    var productId: Int = 0

    fun getProductDetails() {
        viewModelScope.launch {
            getProductDetailsUseCase.execute(productId).onSuccess {
                _productDetailsLiveData.value = it
                _quantityLiveData.value = it.addToCart.quantity
            }.onFailure {
                Log.d("api error", it.message.toString())
            }
        }
    }


    fun addToCart() {
        val cartDetails = CartDetails(id = productId, quantity = 1)
        viewModelScope.launch { addToCartUseCase.execute(cartDetails) }
        _quantityLiveData.value = _quantityLiveData.value!!.plus(1)
    }

    fun increaseQuantity() {
        _quantityLiveData.value = _quantityLiveData.value!!.plus(1)
        updateQuantityRemotly()
    }

    fun decreaseQuantity() {
        if (_quantityLiveData.value!! > 0) {
            _quantityLiveData.value = _quantityLiveData.value!!.minus(1)
            updateQuantityRemotly()
        }
    }

    fun updateQuantityRemotly() {
        viewModelScope.launch {
            updateCartUserCase.execute(CartDetails(productId, _quantityLiveData.value!!))
        }
    }

    private val similarProductsList = mutableListOf<ProductOverview>()

    fun getSimilarProducts(productDetails: Product): MutableList<ProductOverview> {
        similarProductsList.clear()
        productDetails.productTags.forEach {
            similarProductsList.addAll(it.products)
        }
        return similarProductsList
    }

    fun isAuthenticated(): Boolean {
        return getStringSp.execute(CUSTOMER_ACCESS_TOKEN_KEY)?.let {
            true
        } ?: false
    }



}