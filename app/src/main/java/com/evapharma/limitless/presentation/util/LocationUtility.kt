package com.evapharma.limitless.presentation.util

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Build
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.Fragment
import com.bumptech.glide.util.Util
import com.evapharma.limitless.R
import com.evapharma.limitless.presentation.utils.show
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

fun checkLocationPermissions(context: Context) =
    EasyPermissions.hasPermissions(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )


fun requestLocationPermissions(host: Activity) {
    EasyPermissions.requestPermissions(
        host,
        host.getString(R.string.location_permission_request_message),
        LOCATION_PERMISSION_REQUEST_CODE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
}


fun checkGps(
    resolutionForResult: ActivityResultLauncher<IntentSenderRequest>,
    locationSettingsBuilder: LocationSettingsRequest.Builder,
    locationSettingsClient: SettingsClient,
    onSuccessFunction: (() -> Int)? = null,

) {
    val task: Task<LocationSettingsResponse> =
        locationSettingsClient.checkLocationSettings(locationSettingsBuilder.build())
    task.addOnSuccessListener {
        onSuccessFunction?.let {
            onSuccessFunction()
        }
    }
    // if gps is closed -> display request dialogue to open gps
    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution).build()
                resolutionForResult.launch(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
            }
        }
    }
}
