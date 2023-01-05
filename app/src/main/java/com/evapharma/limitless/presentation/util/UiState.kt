package com.evapharma.limitless.presentation.util

sealed class UiState{
    data class FeedUiState<T>(val data: List<T>) : UiState()
    data class Error(val message: String?) : UiState()
    object Loading: UiState()
    object NotLoading : UiState()
}
