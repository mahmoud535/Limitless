package com.evapharma.limitless.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.domain.usecases.cart.ClearCart
import com.evapharma.limitless.domain.usecases.cart.GetCart
import com.evapharma.limitless.domain.usecases.cart.PutProduct
import com.evapharma.limitless.domain.usecases.sp.GetString
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cart: GetCart,
    private val clearCart: ClearCart,
    private val putProduct: PutProduct,
    val getString: GetString
) : ViewModel() {

    private val cartMutable = MutableLiveData<Cart>()
    val cartViewModel: LiveData<Cart> = cartMutable

    private suspend fun getCart() {
        cart.execute().onSuccess {
            cartMutable.value = it
        }.onFailure {
            println("Cart Failed")
        }
    }

    fun refreshCart() {
        viewModelScope.launch {
            getCart()
        }
    }

    fun clearCart() {
        cartMutable.value = Cart(ArrayList(), "", "", "", "", "")
        viewModelScope.launch {
            clearCart.execute()
        }
    }

    fun updateProduct(cartDetails: CartDetails) {
        viewModelScope.launch {
            putProduct.execute(cartDetails)
            getCart()
        }
    }

}