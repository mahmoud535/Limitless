package com.evapharma.limitless.presentation.maps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evapharma.limitless.domain.usecases.GetCurrentLocationUseCase
import com.evapharma.limitless.domain.usecases.sp.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    val putBooleanSp: PutBoolean,
    val putStringSp: PutString,
) : ViewModel(){

    @SuppressLint("MissingPermission")
    fun getLastLocation(fusedClient: FusedLocationProviderClient): Task<Location> {
        return fusedClient.lastLocation

    }

}