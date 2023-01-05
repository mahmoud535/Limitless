package com.evapharma.limitless.presentation.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.R
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.*
import com.evapharma.limitless.domain.usecases.cart.AddToCartUseCase
import com.evapharma.limitless.domain.usecases.catalog.GetBundleUseCase
import com.evapharma.limitless.domain.usecases.catalog.GetCategories
import com.evapharma.limitless.domain.usecases.catalog.GetOffersUseCase
import com.evapharma.limitless.domain.usecases.sp.*
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import com.evapharma.limitless.presentation.util.CUSTOMER_ACCESS_TOKEN_KEY
import com.evapharma.limitless.presentation.util.GUEST_ACCESS_TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val getBundleUseCase: GetBundleUseCase,
    val getLocation: GetLocationState,
    val getStringSp: GetString,
    val getCategories: GetCategories,
    private val addToCartUseCase: AddToCartUseCase,
    val putString: PutString,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val allBundlesProducts = mutableListOf<Product>()
    var categories: List<Category> = ArrayList<Category>()
    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> = _category

    var categoryExpand: Boolean = true

    private val _offers = MutableLiveData<List<Product>>()
    val offers: LiveData<List<Product>> = _offers

    private var _slider: MutableLiveData<List<Banner>>
    lateinit var slider: LiveData<List<Banner>>

    private val _cartLoading = MutableLiveData<Boolean>()
    val cartLoading: LiveData<Boolean> = _cartLoading

    private val _carouselList: MutableLiveData<List<Carousel>>
    var carouselList: LiveData<List<Carousel>>

    private val _bundles: MutableLiveData<List<BundleData>> = MutableLiveData()
    val bundleLiveData: LiveData<List<BundleData>> = _bundles

    init {
        _carouselList = MutableLiveData()
        carouselList = _carouselList
        _slider = MutableLiveData()
        slider = _slider
        loadCategory()
        getOffers()
        getSliderList()
        getCarouselList()
        //  refreshToken()
    }


    private suspend fun getCategories() {
        getCategories.execute().onSuccess {
            _category.value = it
        }.onFailure {
            println("error111" + it.message)
        }
    }

    private fun loadCategory() = viewModelScope.launch {
        getCategories()
    }

    private fun getOffers() {
        viewModelScope.launch {
            getOffersUseCase.execute().onSuccess {
                _offers.value = it.filter { it.hasDiscount }
            }
        }
    }


    private suspend fun getBundles() {
        allBundlesProducts.clear()
        getBundleUseCase.execute().onSuccess {
            _bundles.value = it
            it.forEach { bundle ->
                allBundlesProducts.addAll(bundle.products)
            }
        }.onFailure {
            println("api Error ${it.message}")
        }
    }

    fun refreshBundle() {
        viewModelScope.launch {
            getBundles()
        }
    }

    fun addToCart(productId: Int) {
        _cartLoading.value = true
        val cartDetails = CartDetails(id = productId, quantity = 1)
        viewModelScope.launch {
            addToCartUseCase.execute(cartDetails)
            _cartLoading.postValue(false)
        }
    }


    private fun getSliderList() {
        val sliderList = listOf<Banner>(Banner(R.drawable.slider1), Banner(R.drawable.slider2))
        _slider.value = sliderList
    }

    private fun getCarouselList() {
        val carouselList = listOf<Carousel>(
            Carousel(R.drawable.carousel1, context.getString(R.string.carousel1_label)),
            Carousel(R.drawable.carousel2, context.getString(R.string.carousel2_label)),
            Carousel(R.drawable.carousel3, context.getString(R.string.carousel3_label))
        )
        _carouselList.value = carouselList
    }

    private fun refreshToken() {
        getStringSp.execute(CUSTOMER_ACCESS_TOKEN_KEY)?.let {
            ApiTokenSingleton.API_TOKEN = it
        } ?: run { ApiTokenSingleton.API_TOKEN = getStringSp.execute(GUEST_ACCESS_TOKEN_KEY) ?: "" }
    }

    fun isAuthenticated(): Boolean {
        return getStringSp.execute(CUSTOMER_ACCESS_TOKEN_KEY)?.let {
            true
        } ?: false
    }


}