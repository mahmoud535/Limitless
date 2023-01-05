package com.evapharma.limitless.presentation.checkout

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.ActivityCheckoutBinding
import com.evapharma.limitless.domain.model.Summary
import com.evapharma.limitless.presentation.util.*
import com.evapharma.limitless.presentation.util.CheckoutProcess.SHIPPING
import com.evapharma.limitless.presentation.util.CheckoutProcess.PAYMENT
import com.evapharma.limitless.presentation.util.CheckoutProcess.SUMMARY
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
class CheckoutActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var locationSettingsBuilder: LocationSettingsRequest.Builder

    @Inject
    lateinit var locationSettingsClient: SettingsClient

    lateinit var binding: ActivityCheckoutBinding

    private val viewModel: CheckoutViewModel by viewModels()


    private var resolutionForResult: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { showCheckoutLayout() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        getCartSummary()
        binding.checkoutToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setContentView(binding.root)
        requestLocationPermission()
    }


    fun setFirstProcess() = setProcessBackground(SHIPPING)
    fun setSecondProcess() = setProcessBackground(PAYMENT)
    fun setThirdProcess() = setProcessBackground(SUMMARY)


    fun unsetFirstProcess() = unsetProcessBackground(SHIPPING)
    fun unsetSecondProcess() = unsetProcessBackground(PAYMENT)
    fun unsetThirdProcess() = unsetProcessBackground(SUMMARY)


    private fun setProcessBackground(process: CheckoutProcess) {
        when (process) {
            SHIPPING -> {
                setCurrentProcess(binding.firstProcess, binding.shippingText)
            }
            PAYMENT -> {
                setCurrentProcess(binding.secondProcess, binding.paymentText)
                binding.firstDivider.dividerColor = ContextCompat.getColor(this, R.color.green)
            }
            SUMMARY -> {
                setCurrentProcess(binding.thirdProcess, binding.summaryText)
                binding.secondDivider.dividerColor = ContextCompat.getColor(this, R.color.green)
            }
        }
    }

    private fun unsetProcessBackground(process: CheckoutProcess) {
        when (process) {
            SHIPPING -> {
                unCompletedProcess(binding.firstProcess, binding.shippingText)
            }
            PAYMENT -> {
                unCompletedProcess(binding.secondProcess, binding.paymentText)
                binding.firstDivider.dividerColor = ContextCompat.getColor(this, R.color.grey_700)
            }
            SUMMARY -> {
                unCompletedProcess(binding.thirdProcess, binding.summaryText)
                binding.secondDivider.dividerColor = ContextCompat.getColor(this, R.color.grey_700)
            }
        }
    }

    private fun setCurrentProcess(v1: TextView, v2: TextView) {
        v1.background = ContextCompat.getDrawable(this, R.drawable.current_process_shape)
        v1.setTextColor(ContextCompat.getColor(this, R.color.white))
        v2.setTextColor(ContextCompat.getColor(this, R.color.green))
    }

    private fun unCompletedProcess(v1: TextView, v2: TextView) {
        v1.background = ContextCompat.getDrawable(this, R.drawable.uncompleted_process)
        v1.setTextColor(ContextCompat.getColor(this, R.color.grey_700))
        v2.setTextColor(ContextCompat.getColor(this, R.color.grey_700))
    }


    private val showCheckoutLayout = {
        binding.checkoutLayout.show()
        0
    }

    private fun getCartSummary() {
        val summary = intent.getParcelableExtra<Summary>(SUMMARY_KEY)
        summary?.let {
            viewModel.summary = summary
        }
    }

    private fun requestLocationPermission() {
        if (checkLocationPermissions(this)) {
            checkGps(
                resolutionForResult,
                locationSettingsBuilder,
                locationSettingsClient,
                onSuccessFunction = showCheckoutLayout
            )
            binding.checkoutLayout.show()
            return
        }
        requestLocationPermissions(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkGps(
            resolutionForResult,
            locationSettingsBuilder,
            locationSettingsClient,
            onSuccessFunction = showCheckoutLayout
        )
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

}