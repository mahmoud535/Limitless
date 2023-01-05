package com.evapharma.limitless.presentation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.domain.model.Category
import com.evapharma.limitless.domain.model.ProductsByCategory
import com.evapharma.limitless.domain.usecases.catalog.GetCategories
import com.evapharma.limitless.domain.usecases.catalog.GetProductsByCategory
import com.evapharma.limitless.domain.util.onFailure
import com.evapharma.limitless.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProducts: GetProductsByCategory,
    val getCategories: GetCategories
) : ViewModel() {

    var grid = true
    private val _category = MutableLiveData<List<Category>>()
    val category:LiveData<List<Category>> = _category

    private suspend fun categories(){
        getCategories.execute().onSuccess {
            _category.value = it
        }.onFailure {
            println("ERROR")
        }
    }

    fun loadCategory() = viewModelScope.launch {
        categories()
    }

    var productsByCategory: ProductsByCategory? = null
    private val _products = MutableLiveData<ProductsByCategory>()
    val products: LiveData<ProductsByCategory> = _products

    private suspend fun executeProduct(categoryId: Int) {
        getProducts.execute(categoryId).onSuccess {
            _products.value = it
        }.onFailure {
        }
    }

    fun refresh(categoryId: Int) {
        viewModelScope.launch {
            executeProduct(categoryId)
        }
    }


}