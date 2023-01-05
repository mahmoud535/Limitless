package com.evapharma.limitless.presentation.home

import androidx.lifecycle.LiveData

interface OnItemClickListener {
    fun <T> onItemClick(item: T)
    fun onItemAddToCartClick (productId : Int) : LiveData<Boolean>?

}