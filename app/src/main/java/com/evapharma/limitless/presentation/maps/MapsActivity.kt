package com.evapharma.limitless.presentation.maps

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.evapharma.limitless.databinding.ActivityMapsBinding
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.util.SupportClass
import com.evapharma.limitless.presentation.util.checkGps
import com.evapharma.limitless.presentation.util.checkLocationPermissions
import com.evapharma.limitless.presentation.util.requestLocationPermissions
import com.evapharma.limitless.presentation.utils.show
import com.evapharma.limitless.presentation.utils.showSnackBar
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMapsBinding

    @Inject
    lateinit var locationSettingsBuilder: LocationSettingsRequest.Builder

    @Inject
    lateinit var locationSettingsClient: SettingsClient

    private var resolutionForResult: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                navigateToMapView()
            }
            else
                navigateToMainActivity()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        loadFragment(MapsFragment())
    }

    private val navigateToMapView = {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()
    }

    private fun navigateToMainActivity() {
        SupportClass.startActivityWithFlags(this@MapsActivity, MainActivity::class.java)
        Toast.makeText(this, "No location access granted.", Toast.LENGTH_SHORT).show()
        finish()
    }

    lateinit var fragment: Fragment
    private fun loadFragment(fragment: Fragment?) {
        this.fragment = fragment!!

        if (checkLocationPermissions(this))
            checkGps(
                resolutionForResult,
                locationSettingsBuilder,
                locationSettingsClient,
                navigateToMapView
            )
        else
            requestLocationPermissions(this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkGps(
            resolutionForResult,
            locationSettingsBuilder,
            locationSettingsClient,
            navigateToMapView
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        navigateToMainActivity()
    }
}